package fr.kubys.leekscriptv4.api.dto;

public class AIResponse {
    private AI ai;

    public boolean isSuccess() {
        return ai != null;
    }

    public AI getAi() {
        return ai;
    }

    public static class AI {
        private Integer id;
        private String name;
        private Integer level;
        private Boolean valid;
        private Integer owner;
        private String code;

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Integer getLevel() {
            return level;
        }

        public Boolean getValid() {
            return valid;
        }

        public Integer getOwner() {
            return owner;
        }

        public String getCode() {
            return code;
        }
    }
}
