package pl.sda.dto;

public class CategoryDto {
    private Long id;
    private String name;
    private String description;
    private byte[] picture;

    public CategoryDto(Long id, String name, String description, byte[] picture) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.picture = picture;
    }

    public CategoryDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
}
