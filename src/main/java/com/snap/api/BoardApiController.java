package com.snap.api;

import com.snap.domain.*;
import com.snap.dto.board.BoardDto;
import com.snap.dto.board.BoardListResponse;
import com.snap.dto.board.UserBoardResponse;
import com.snap.errorhandling.dto.Code;
import com.snap.errorhandling.dto.DataResponseDto;
import com.snap.errorhandling.dto.ErrorResponseDto;
import com.snap.errorhandling.dto.ResponseDto;
import com.snap.repository.BoardRepository;
import com.snap.repository.CategoryRepository;
import com.snap.repository.TagRepository;
import com.snap.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.lang.reflect.Array;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardApiController {
    private final BoardService boardService;
    private  final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final BoardRepository boardRepository;

    @GetMapping("/api/board/categoryList")
    public ResponseDto CategoryList(){
        try {
            List<Category> categories = categoryRepository.findAll();
            if(categories.isEmpty()){
                return DataResponseDto.empty();
            }else{
                return DataResponseDto.of(categories);
            }

        }catch (Exception e){
            return ErrorResponseDto.of(Code.VALIDATION_ERROR,e.getMessage());
        }
    }
    @GetMapping("/api/board/tagList")
    public ResponseDto tagList(){
        try {
            List<Tag> tagList = boardRepository.findTopTagList();
            if(tagList.isEmpty()){
                return DataResponseDto.empty();
            }else{
                return DataResponseDto.of(tagList);
            }

        }catch (Exception e){
            return ErrorResponseDto.of(Code.VALIDATION_ERROR,e.getMessage());
        }
    }

    @PostMapping("/api/board/save")
    public ResponseDto boardSave(
            @RequestPart(value = "boardRequestDto") BoardDto.BoardRequestDto boardRequestDto
            ,@RequestPart(value = "files") List<MultipartFile> files
            ,Principal principal){
        try {
            boardService.save(boardRequestDto,files, principal.getName());
            return DataResponseDto.empty();
        }catch (Exception e){
            return ErrorResponseDto.of(Code.VALIDATION_ERROR,e.getMessage());
        }
    }

    @GetMapping("/api/board/myBoard")
    public ResponseDto userBoard(@RequestParam String type, Principal principal){
        try {
            List<UserBoardResponse> userBoardList = boardService.getUserBoardList(type.toUpperCase(), principal.getName());
            return DataResponseDto.of(userBoardList);
        }catch (Exception e){
            return ErrorResponseDto.of(Code.VALIDATION_ERROR,e.getMessage());
        }
    }

    @GetMapping("/api/board/list")
    public ResponseDto boardList(@RequestParam String tagId, Principal principal){
        try {
            List<BoardListResponse> boardList = boardService.getBoardList(tagId, principal.getName());
            return DataResponseDto.of(boardList);
        }catch (Exception e){
            return ErrorResponseDto.of(Code.VALIDATION_ERROR,e.getMessage());
        }
    }
}
