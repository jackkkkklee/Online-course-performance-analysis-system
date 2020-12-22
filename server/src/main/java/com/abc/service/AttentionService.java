package com.abc.service;

import java.io.IOException;

public interface AttentionService {
    int finalAssessment(String base64,String mode,String sid) throws IOException;
    int AssessmentByImage(String mode,String sid,String imageName);
    String[] basicAssessment(String sid, String imageName);


}
