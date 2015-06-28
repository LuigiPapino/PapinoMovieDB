package net.dragora.papinomoviedb.model;


import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

/**
 * Created by nietzsche on 27/06/15.
 */

@Generated("org.jsonschema2pojo")
public class GenreMovie {

    @Expose
    private Integer id;
    @Expose
    private String name;

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

}