package com.e.salestracker.Modal;

public class Model_cona_cateogry {

    private String Architect;
    private String Builder;
    private String Company;
    private String Contractor;
    private String Dealer;
    private String Electrician;
    private String Retailer;

    private String collection_value;
    private String order_value;
    private String client_name;
    private String category;

    public Model_cona_cateogry(){}

  /*  public Model_cona_cateogry(String collection_value,
                               String order_value,
                               String client_name,
                               String category) {
        this.collection_value = collection_value;
        this.order_value = order_value;
        this.client_name = client_name;
        this.category = category;
    }*/

    public String getCollection_value() {
        return collection_value;
    }

    public void setCollection_value(String collection_value) {
        this.collection_value = collection_value;
    }

    public String getOrder_value() {
        return order_value;
    }

    public void setOrder_value(String order_value) {
        this.order_value = order_value;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

   /* public Model_cona_cateogry(
            String Architect,
            String Builder,
            String Company,
            String Contractor,
            String Dealer,
            String Electrician,
            String Retailer) {

        this.Architect = Architect;
        this.Builder = Builder;
        this.Company = Company;
        this.Contractor = Contractor;
        this.Dealer = Dealer;
        this.Electrician = Electrician;
        this.Retailer = Retailer;
    }*/

    public String getArchitect() {
        return Architect;
    }

    public void setArchitect(String architect) {
        Architect = architect;
    }

    public String getBuilder() {
        return Builder;
    }

    public void setBuilder(String builder) {
        Builder = builder;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public String getContractor() {
        return Contractor;
    }

    public void setContractor(String contractor) {
        Contractor = contractor;
    }

    public String getDealer() {
        return Dealer;
    }

    public void setDealer(String dealer) {
        Dealer = dealer;
    }

    public String getElectrician() {
        return Electrician;
    }

    public void setElectrician(String electrician) {
        Electrician = electrician;
    }

    public String getRetailer() {
        return Retailer;
    }

    public void setRetailer(String retailer) {
        Retailer = retailer;
    }
}
