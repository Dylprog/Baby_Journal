package com.example.babyjournal;

public class FamilyMember {
    private String relation;
    private String name;

    public FamilyMember(String relation, String name) {
        this.relation = relation;
        this.name = name;
    }

    public String getRelation() { return relation; }
    public String getName() { return name; }
} 