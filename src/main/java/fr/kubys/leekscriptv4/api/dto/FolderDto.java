package fr.kubys.leekscriptv4.api.dto;

public class FolderDto {
    private Integer id;
    private String name;
    private Integer folder;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getParent() {
        return folder;
    }

    public String buildName() {
        return String.format("%s__%s", name, id);
    }
}
