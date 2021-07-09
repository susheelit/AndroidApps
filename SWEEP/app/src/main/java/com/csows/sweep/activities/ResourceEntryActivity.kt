package com.csows.sweep.activities

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import com.csows.sweep.R
import com.csows.sweep.controller.ApiClient
import com.csows.sweep.models.ElcCategoryl
import com.csows.sweep.models.ELCList
import com.csows.sweep.models.ResourceList
import com.csows.sweep.models.ResourceMaterial
import com.csows.sweep.utils.*
import com.csows.sweep.utils.ImageUtils.*
import com.csows.sweep.utils.Permission.Companion.cameraPermission
import com.csows.sweep.utils.Permission.Companion.requestCameraPermission
import com.csows.sweep.utils.Permission.Companion.requestStoragePermission
import com.csows.sweep.utils.Permission.Companion.storagePermission
import com.csows.sweep.utils.ShowDialog.Companion.alertError
import com.csows.sweep.utils.ShowDialog.Companion.toastShow
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_resource_entry.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*

class ResourceEntryActivity : AppCompatActivity(), OnItemSelectedListener {

    companion object {
        private lateinit var mCtx: Context
        private lateinit var userPreferences: UserPreferences
        private val TAG = ResourceEntryActivity::class.java.simpleName
        private var processDialog: Dialog? = null
        private var elcID = 0
        private var resourceID = 0
        private val cameraCode = 100
        private val galleryCode = 200
        private val fileCode = 300
        private val videoCode = 400
        private val audioCode = 500
        private var pdfFile: File? = null
        private var photoFile: File? = null
        private var picturePath = ""
        private var imgExt = ""
        private var fileExt = ""

        private var encodedImage = ""
        private var photoName = ""
        private var encodedFile = "1"
        private var fileName = "1"
        private var imgBitmap: Bitmap? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resource_entry)
        initView()
    }

    private fun initView() {
        this.also { mCtx = it }
        toolbarWithBackAndTitle(toolbar, mCtx, "Resource Entry")
        userPreferences = UserPreferences(mCtx)
        getELCList()

        btnSubmit.setOnClickListener {
            validateField()
        }

        btnAttachement.setOnClickListener {
            addAttachement()
        }
    }

    private fun addAttachement() {
        if (resourceID != 0) {
            // resourceID 1=pdf, 2=Image, 3=mp4, 4=mp3
            if (resourceID == 1) {
                // pdf
                uploadFile()
            } else if (resourceID == 2) {
                // images
                if (cameraPermission(mCtx)) {
                    selectImage()
                } else {
                    requestCameraPermission(mCtx)
                }
            } else if (resourceID == 3) {
                // Video mp4
                selectVideo()
            } else if (resourceID == 4) {
                // audio mp3
                selectAudio()
            }
        } else {
            alertError("Please select resource", mCtx)
        }
    }

    private fun uploadFile() {
        if (storagePermission(mCtx)) {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "application/pdf"
            startActivityForResult(intent, fileCode)
        } else {
            requestStoragePermission(mCtx)
        }
    }

    private fun selectImage() {
        val options = arrayOf("Take Photo", "Choose from Gallery", "Cancel")
        val builder = AlertDialog.Builder(mCtx)
        builder.setTitle("Add Photo!")
        builder.setItems(options, DialogInterface.OnClickListener { dialog, which ->
            if (options[which] == "Take Photo") {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, cameraCode)
            } else if (options[which] == "Choose from Gallery") {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, galleryCode)
            } else if (options[which] == "Cancel") {
                dialog.dismiss()
            }
        })
        builder.show()
    }

    private fun selectVideo() {
        if (storagePermission(mCtx)) {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "video/*"
            startActivityForResult(Intent.createChooser(intent, "Select Video"), audioCode)
        } else {
            requestStoragePermission(mCtx)
        }

    }

    private fun selectAudio() {
        if (storagePermission(mCtx)) {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "audio/*"
            startActivityForResult(Intent.createChooser(intent, "Select Audio"), audioCode)
        } else {
            requestStoragePermission(mCtx)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            cameraCode -> {
                try {
                    val thumbnail: Bitmap = data?.extras?.get("data") as Bitmap
                    val bytes: ByteArrayOutputStream = ByteArrayOutputStream()
                    thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
                    photoFile = File(
                        Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis().toString().plus(".jpg")
                    )
                    picturePath = photoFile!!.getPath()
                    imgExt = getfileExtension(photoFile!!.getName())

                    val fo: FileOutputStream
                    try {
                        photoFile!!.createNewFile()
                        fo = FileOutputStream(photoFile)
                        fo.write(bytes.toByteArray())
                        fo.close()
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                    val nh: Int = thumbnail.height * (512 / thumbnail.width)
                    val scaledBitmap = Bitmap.createScaledBitmap(thumbnail, 512, nh, false)
                    setImage(scaledBitmap)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            galleryCode -> {
                try {
                    val selectedImage: Uri? = data?.data
                    val filePath = arrayOf(MediaStore.Images.Media.DATA)
                    val c: Cursor? = selectedImage?.let {
                        mCtx.contentResolver.query(it, filePath, null, null, null)
                    }
                    c?.moveToFirst()
                    val columnIndex = c!!.getColumnIndex(filePath[0])
                    picturePath = c.getString(columnIndex)
                    photoFile = File(picturePath)
                    imgExt = getfileExtension(photoFile!!.name)
                    c.close()
                    val thumbnail: Bitmap = getBitmap(picturePath)
                    val bytes = ByteArrayOutputStream()
                    thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
                    setImage(thumbnail)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            fileCode -> {
                try {
                    if (null != data) {
                        val uri: Uri? = data.data
                        pdfFile = getFilePathFromURI(mCtx, uri)
                        // pdfFile = new File(RealPathUtil.getRealPath(mContext, uri));
                        //   fileExt = UserContext.getfileExtension(pdfFile.getName());
                        fileExt = ".pdf"
                        tvFileName.text = pdfFile.toString()
                        encodedFile = fileString("" + pdfFile)
                        fileName =
                            "File_" + System.currentTimeMillis() + generateRandomNo() + fileExt

                        if (TextUtils.isEmpty(encodedFile)) {
                            toastShow("Unsupported File", mCtx)
                            tvFileName.text = ""
                        }

                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun setImage(thumbnail: Bitmap) {
        imgBitmap = thumbnail
        //val nh: Int = thumbnail.height * (512 / thumbnail.width)
        //  val scaledBitmap = Bitmap.createScaledBitmap(thumbnail, 512, nh, false)

        ivImage.setImageBitmap(imgBitmap)
        ivImage.visibility = View.VISIBLE
        val picByteArray = getByteArray(imgBitmap)
        encodedImage = Base64.encodeToString(picByteArray, Base64.DEFAULT)
        photoName = "Photo_".plus(
            System.currentTimeMillis().toString().plus(generateRandomNo().toString()).plus(imgExt)
        )
        tvFileName.text = photoName
    }

    private fun validateField() {
        alertError("Success", mCtx)
    }

    private fun getELCList() {
        if (ConnectionDetector.isConnectedToInernet(mCtx)) {
            processDialog = CustomProgressDialog.showDialog(mCtx, "Getting Details... ")

            val call = ApiClient.getClient().getELCList()
            call?.enqueue(object : Callback<ELCList?> {
                override fun onResponse(call: Call<ELCList?>, response: Response<ELCList?>) {

                    processDialog?.let { CustomProgressDialog.closeDialog(it) }
                    if (response.isSuccessful) {
                        Log.e(TAG, "response " + Gson().toJson(response.body()))
                        var responseList = response.body()
                        if (null != responseList) {
                            getResourceList(responseList)
                        } else {
                            ShowDialog.toastShow(resources.getString(R.string.went_wrong), mCtx)
                        }
                    } else {
                        Log.e(TAG, response.errorBody().toString())
                        ShowDialog.toastShow(resources.getString(R.string.went_wrong), mCtx)
                    }

                }

                override fun onFailure(call: Call<ELCList?>, t: Throwable) {
                    Log.e(TAG, "onFailure " + t.message)
                    ShowDialog.toastShow(resources.getString(R.string.went_wrong), mCtx)
                    processDialog?.let { CustomProgressDialog.closeDialog(it) }
                }
            })

        } else {
            ShowDialog.toastShow(resources.getString(R.string.no_internet), mCtx)
        }
    }

    private fun getResourceList(elcList: ELCList) {
        if (ConnectionDetector.isConnectedToInernet(mCtx)) {
            processDialog = CustomProgressDialog.showDialog(mCtx, "Getting Details... ")

            val call = ApiClient.getClient().getListResourceList()
            call?.enqueue(object : Callback<ResourceList?> {
                override fun onResponse(
                    call: Call<ResourceList?>,
                    response: Response<ResourceList?>
                ) {

                    processDialog?.let { CustomProgressDialog.closeDialog(it) }

                    if (response.isSuccessful) {
                        Log.e(TAG, "response " + Gson().toJson(response.body()))
                        var responseList = response.body()
                        if (null != responseList) {
                            bindSpinner(elcList, responseList)
                        } else {
                            toastShow(resources.getString(R.string.went_wrong), mCtx)
                        }
                    } else {
                        Log.e(TAG, response.errorBody().toString())
                        toastShow(resources.getString(R.string.went_wrong), mCtx)
                    }
                }

                override fun onFailure(call: Call<ResourceList?>, t: Throwable) {
                    Log.e(TAG, "onFailure " + t.message)
                    ShowDialog.toastShow(resources.getString(R.string.went_wrong), mCtx)
                    processDialog?.let { CustomProgressDialog.closeDialog(it) }
                }
            })

        } else {
            toastShow(resources.getString(R.string.no_internet), mCtx)
        }
    }

    private fun bindSpinner(elcList: ELCList, resourceList: ResourceList) {
        // bind ELC List
        elcList?.let {
            val elcItemList: MutableList<ElcCategoryl> = ArrayList()

            var hint = ElcCategoryl(0, "Select ELC")
            elcItemList.add(hint)
            elcItemList.addAll(elcList.ddlElcCategoryl)

            var elcList = ArrayAdapter<ElcCategoryl>(
                mCtx,
                android.R.layout.simple_spinner_dropdown_item,
                elcItemList
            )
            elcList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spElc.adapter = elcList

            spElc.onItemSelectedListener = this
        }

        // bind resource List
        resourceList?.let {

            val resourceItemList: MutableList<ResourceMaterial> = ArrayList()

            var hint = ResourceMaterial(0, "Select Resource", "")
            resourceItemList.add(hint)
            resourceItemList.addAll(resourceList.ddlResourceMaterial)

            var resourceList = ArrayAdapter<ResourceMaterial>(
                mCtx,
                android.R.layout.simple_spinner_dropdown_item,
                resourceItemList
            )

            resourceList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spResource.adapter = resourceList

            spResource.onItemSelectedListener = this
        }

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        when (parent) {
            spElc -> {
                val id: ElcCategoryl = parent?.getItemAtPosition(position) as ElcCategoryl
                elcID = id.ElcCategoryID
                if (position != 0) {
                    Log.e(TAG, "elcID : $elcID")
                    if (resourceID != 0) {
                        btnEnable(btnSubmit)
                    } else {
                        btnDisable(btnSubmit)
                    }
                } else {
                    btnDisable(btnSubmit)
                }

            }

            spResource -> {
                val id: ResourceMaterial = parent?.getItemAtPosition(position) as ResourceMaterial
                resourceID = id.ResourceID
                resetView()
                if (position != 0) {
                    btnEnable(btnAttachement)
                    Log.e(TAG, "resource : $resourceID")
                    if (elcID != 0) {
                        btnEnable(btnSubmit)
                    } else {
                        btnDisable(btnSubmit)
                    }
                } else {
                    btnDisable(btnAttachement)
                    btnDisable(btnSubmit)
                }
            }
        }

    }

    private fun resetView() {
        ivImage.visibility = View.GONE

        pdfFile = null
        photoFile = null
        picturePath = ""
        imgExt = ""
        fileExt = ""
        encodedImage = ""
        photoName = ""
        encodedFile = "1"
        fileName = "1"
        imgBitmap = null

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        btnDisable(btnSubmit)
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }


}