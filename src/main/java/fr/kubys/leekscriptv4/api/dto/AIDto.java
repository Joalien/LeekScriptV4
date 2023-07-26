package fr.kubys.leekscriptv4.api.dto;

public class AIDto {
    private Integer id;
    private String name;
    private Boolean valid;
    private Integer folder;
    private String version;
    private Boolean strict;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Boolean getValid() {
        return valid;
    }

    public Integer getParent() {
        return folder;
    }

    public String getVersion() {
        return version;
    }

    public Boolean getStrict() {
        return strict;
    }
}
