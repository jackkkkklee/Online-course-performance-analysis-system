package com.abc.service;

public interface AttentionService {
    int finalAssessment(String base64,String mode,String sid);
    int AssessmentByImage(String mode,String sid,String imageName);
    String[] basicAssessment(String sid, String imageName);


}
