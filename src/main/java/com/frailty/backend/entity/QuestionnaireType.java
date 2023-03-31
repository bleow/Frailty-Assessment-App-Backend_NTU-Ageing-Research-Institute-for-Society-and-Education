package com.frailty.backend.entity;

public enum QuestionnaireType {
    PHENOTYPE("Frailty Phenotype Questionnaire"),
    IPAQ("IPAQ-E"),
    SARC("SARC-F");

    private String displayName;

    QuestionnaireType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
