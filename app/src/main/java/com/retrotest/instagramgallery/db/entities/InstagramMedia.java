package com.retrotest.instagramgallery.db.entities;

import com.retrotest.instagramgallery.api.dto.Media;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Damir Burazerovic on 8/4/15.
 */
public class InstagramMedia extends RealmObject {

    private static final int LOW_RES_MEDIA = 320;

    @PrimaryKey
    private String id;

    // link to instagram webpage for media
    private String link;

    // date of creation
    private long   createdTime;

    // link to low resolution image ()
    private String lowResUrl;
    private String standardResUrl;
    private String thumbnailResUrl;

    private boolean isDeleted;

    public InstagramMedia() {
    }

    public InstagramMedia(Media media) {
        this.id = media.getId();
        this.link = media.getLink();
        this.createdTime = media.getCreatedTime();
        if (media.getImages() != null) {

            if (media.getImages().getLowResolution() != null) {
                this.lowResUrl = media.getImages().getLowResolution().getUrl();
            }

            if (media.getImages().getThumbnail() != null) {
                this.thumbnailResUrl = media.getImages().getThumbnail().getUrl();
            }

            if (media.getImages().getStandardResolution() != null) {
                this.standardResUrl = media.getImages().getStandardResolution().getUrl();
            }
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public String getLowResUrl() {
        return lowResUrl;
    }

    public void setLowResUrl(String lowResUrl) {
        this.lowResUrl = lowResUrl;
    }

    public String getStandardResUrl() {
        return standardResUrl;
    }

    public void setStandardResUrl(String standardResUrl) {
        this.standardResUrl = standardResUrl;
    }

    public String getThumbnailResUrl() {
        return thumbnailResUrl;
    }

    public void setThumbnailResUrl(String thumbnailResUrl) {
        this.thumbnailResUrl = thumbnailResUrl;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
