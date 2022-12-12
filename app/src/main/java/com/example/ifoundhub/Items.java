package com.example.ifoundhub;

public class Items {


    String Status, Item_Name, Item_Description, Date_Reported, Location;
    String Last_Name, First_Name, Middle_Name, Student_Number, College, Year, Course, Block;
    String Contact_Number;
    String Image_Url;

    public Items(String status, String item_Name, String item_Description, String date_Reported, String location, String last_Name, String first_Name, String middle_Name, String student_Number, String college, String year, String course, String block, String contact_Number, String image_Url) {
        Status = status;
        Item_Name = item_Name;
        Item_Description = item_Description;
        Date_Reported = date_Reported;
        Location = location;
        Last_Name = last_Name;
        First_Name = first_Name;
        Middle_Name = middle_Name;
        Student_Number = student_Number;
        College = college;
        Year = year;
        Course = course;
        Block = block;
        Contact_Number = contact_Number;
        Image_Url = image_Url;
    }




    public Items() {
    }

    public String getContact_Number() {
        return Contact_Number;
    }

    public void setContact_Number(String contact_Number) {
        Contact_Number = contact_Number;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getLast_Name() {
        return Last_Name;
    }

    public void setLast_Name(String last_Name) {
        Last_Name = last_Name;
    }

    public String getFirst_Name() {
        return First_Name;
    }

    public void setFirst_Name(String first_Name) {
        First_Name = first_Name;
    }

    public String getMiddle_Name() {
        return Middle_Name;
    }

    public void setMiddle_Name(String middle_Name) {
        Middle_Name = middle_Name;
    }

    public String getStudent_Number() {
        return Student_Number;
    }

    public void setStudent_Number(String student_Number) {
        Student_Number = student_Number;
    }

    public String getCollege() {
        return College;
    }

    public void setCollege(String college) {
        College = college;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getCourse() {
        return Course;
    }

    public void setCourse(String course) {
        Course = course;
    }

    public String getBlock() {
        return Block;
    }

    public void setBlock(String block) {
        Block = block;
    }






    public String getItem_Name() {
        return Item_Name;
    }

    public void setItem_Name(String item_Name) {
        Item_Name = item_Name;
    }

    public String getItem_Description() {
        return Item_Description;
    }

    public void setItem_Description(String item_Description) {
        Item_Description = item_Description;
    }

    public String getDate_Reported() {
        return Date_Reported;
    }

    public void setDate_Reported(String date_Reported) {
        Date_Reported = date_Reported;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getImage_Url() {
        return Image_Url;
    }

    public void setImage_Url(String image_Url) {
        Image_Url = image_Url;
    }

    //    String ItemName, itemdescription,  itemDate, itemLocation;
//    String ImageUrl;
//
//    public Items(String itemName, String imageUrl) {
//        ItemName = itemName;
//        ImageUrl = imageUrl;
//    }
//
//    public Items() {
//    }
//
//    public String getItemName() {
//        return ItemName;
//    }
//
//    public void setItemName(String itemName) {
//        ItemName = itemName;
//    }
//
//    public String getImageUrl() {
//        return ImageUrl;
//    }
//
//    public void setImageUrl(String imageUrl) {
//        ImageUrl = imageUrl;
//    }


}
