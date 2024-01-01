package com.snap.dto.board;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.List;


public class BoardDto {
    @Data
    public static class BoardRequestDto{
        @NotEmpty
        private String tagList;
        @NotEmpty
        private Long categoryId;
    }

    @Data
    public static class Photo{
        private String origFileName;
        private String filePath;
    }

    @Data
    public static class userBoardRequestDto{
        @NotEmpty
        private String type; //작성한글, 좋아요한 글


    }
}
