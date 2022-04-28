package com.example.toigether.items;

public class OrgTeam {
    private String org_id;
    private String member_id;

    public OrgTeam(String org_id, String member_id) {
        this.org_id = org_id;
        this.member_id = member_id;
    }

    public OrgTeam() {
    }

    public String getOrg_id() {
        return org_id;
    }

    public void setOrg_id(String org_id) {
        this.org_id = org_id;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }
}
