package com.lohasfarm.kim.ninegagofflinetest.unit;

import java.util.Date;

/**
 * Created by kim on 2015-11-18.
 */
public class Gag {
    //A Gag has
    //      ID        IMG         TITLE       POINTS          COMMENTS
    //ex)   1          1         If Four..      1152            458
    private int _id;
    private int _image;
    private String _title;
    private int _points;
    private int _comments;
    private Date _postDate;

    public Date get_postDate() {
        return _postDate;
    }

    public void set_postDate(Date _postDate) {
        this._postDate = _postDate;
    }

    @Override
    public String toString() {
        return "Gag{" +
                "_id=" + _id +
                ", _image=" + _image +
                ", _title='" + _title + '\'' +
                ", _points=" + _points +
                ", _comments=" + _comments +
                ", _postDate=" + _postDate +
                '}';
    }

    public Gag() {
        _id = 0;
        _image = 0;
        _title = "";
        _points = 0;
        _comments = 0;
        _postDate = new Date();
    }

    public int get_comments() {
        return _comments;
    }

    public void set_comments(int _comments) {
        this._comments = _comments;
    }

    public int get_points() {
        return _points;
    }

    public void set_points(int _points) {
        this._points = _points;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public int get_image() {
        return _image;
    }

    public void set_image(int _image) {
        this._image = _image;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

}
