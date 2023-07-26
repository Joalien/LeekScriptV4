package fr.kubys.leekscriptv4.api.dto;

import java.util.List;

public class AIsResponse {
    private List<AIDto> ais;
    private List<FolderDto> folders;

    public List<AIDto> getAis() {
        return ais;
    }

    public List<FolderDto> getFolders() {
        return folders;
    }

    public void setAis(List<AIDto> ais) {
        this.ais = ais;
    }
}
