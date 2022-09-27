package com.yangyi.music.entity;

public class Artist {
    private Long id;

    private String birthday;

    private String country;

    private Integer artistFans;

    private Integer albumNum;

    private String gener;

    private String weight;

    private String language;

    private Integer mvnum;

    private String pic;

    private String upPcUrl;

    private Integer musicNum;

    private String pic120;

    private Integer isStar;

    private String birthplace;

    private String constellation;

    private String contentType;

    private String aArtist;

    private String name;

    private String pic70;

    private String tall;

    private String pic300;

    private String info;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public Integer getArtistFans() {
        return artistFans;
    }

    public void setArtistFans(Integer artistFans) {
        this.artistFans = artistFans;
    }

    public Integer getAlbumNum() {
        return albumNum;
    }

    public void setAlbumNum(Integer albumNum) {
        this.albumNum = albumNum;
    }

    public String getGener() {
        return gener;
    }

    public void setGener(String gener) {
        this.gener = gener == null ? null : gener.trim();
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight == null ? null : weight.trim();
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
    }

    public Integer getMvnum() {
        return mvnum;
    }

    public void setMvnum(Integer mvnum) {
        this.mvnum = mvnum;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    public String getUpPcUrl() {
        return upPcUrl;
    }

    public void setUpPcUrl(String upPcUrl) {
        this.upPcUrl = upPcUrl == null ? null : upPcUrl.trim();
    }

    public Integer getMusicNum() {
        return musicNum;
    }

    public void setMusicNum(Integer musicNum) {
        this.musicNum = musicNum;
    }

    public String getPic120() {
        return pic120;
    }

    public void setPic120(String pic120) {
        this.pic120 = pic120 == null ? null : pic120.trim();
    }

    public Integer getIsStar() {
        return isStar;
    }

    public void setIsStar(Integer isStar) {
        this.isStar = isStar;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace == null ? null : birthplace.trim();
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation == null ? null : constellation.trim();
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType == null ? null : contentType.trim();
    }

    public String getaArtist() {
        return aArtist;
    }

    public void setaArtist(String aArtist) {
        this.aArtist = aArtist == null ? null : aArtist.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPic70() {
        return pic70;
    }

    public void setPic70(String pic70) {
        this.pic70 = pic70 == null ? null : pic70.trim();
    }

    public String getTall() {
        return tall;
    }

    public void setTall(String tall) {
        this.tall = tall == null ? null : tall.trim();
    }

    public String getPic300() {
        return pic300;
    }

    public void setPic300(String pic300) {
        this.pic300 = pic300 == null ? null : pic300.trim();
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info == null ? null : info.trim();
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id +
                ", birthday='" + birthday + '\'' +
                ", country='" + country + '\'' +
                ", artistFans=" + artistFans +
                ", albumNum=" + albumNum +
                ", gener='" + gener + '\'' +
                ", weight='" + weight + '\'' +
                ", language='" + language + '\'' +
                ", mvnum=" + mvnum +
                ", pic='" + pic + '\'' +
                ", upPcUrl='" + upPcUrl + '\'' +
                ", musicNum=" + musicNum +
                ", pic120='" + pic120 + '\'' +
                ", isStar=" + isStar +
                ", birthplace='" + birthplace + '\'' +
                ", constellation='" + constellation + '\'' +
                ", contentType='" + contentType + '\'' +
                ", aArtist='" + aArtist + '\'' +
                ", name='" + name + '\'' +
                ", pic70='" + pic70 + '\'' +
                ", tall='" + tall + '\'' +
                ", pic300='" + pic300 + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}