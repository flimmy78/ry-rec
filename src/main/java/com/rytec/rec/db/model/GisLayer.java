package com.rytec.rec.db.model;

public class GisLayer {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column gislayer.id
     *
     * @mbggenerated Fri Dec 30 09:40:15 CST 2016
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column gislayer.name
     *
     * @mbggenerated Fri Dec 30 09:40:15 CST 2016
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column gislayer.file
     *
     * @mbggenerated Fri Dec 30 09:40:15 CST 2016
     */
    private String file;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column gislayer.id
     *
     * @return the value of gislayer.id
     *
     * @mbggenerated Fri Dec 30 09:40:15 CST 2016
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column gislayer.id
     *
     * @param id the value for gislayer.id
     *
     * @mbggenerated Fri Dec 30 09:40:15 CST 2016
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column gislayer.name
     *
     * @return the value of gislayer.name
     *
     * @mbggenerated Fri Dec 30 09:40:15 CST 2016
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column gislayer.name
     *
     * @param name the value for gislayer.name
     *
     * @mbggenerated Fri Dec 30 09:40:15 CST 2016
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column gislayer.file
     *
     * @return the value of gislayer.file
     *
     * @mbggenerated Fri Dec 30 09:40:15 CST 2016
     */
    public String getFile() {
        return file;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column gislayer.file
     *
     * @param file the value for gislayer.file
     *
     * @mbggenerated Fri Dec 30 09:40:15 CST 2016
     */
    public void setFile(String file) {
        this.file = file == null ? null : file.trim();
    }
}