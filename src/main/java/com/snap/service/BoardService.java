package com.snap.service;

import com.snap.domain.*;
import com.snap.dto.board.BoardDto;
import com.snap.dto.board.BoardListResponse;
import com.snap.dto.board.UserBoardResponse;
import com.snap.errorhandling.exception.ForbiddenException;
import com.snap.errorhandling.exception.GeneralException;
import com.snap.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    private final TagRepository tagRepository;
    private final FileRepository fileRepository;
    private final EntityManager em;

    @Transactional
    public void save(BoardDto.BoardRequestDto boardRequestDto,List<MultipartFile> files,String mem_email) throws IOException {

        Optional<Member> fmember = memberRepository.findById(mem_email);
        Optional<Category> fcategory = categoryRepository.findById(boardRequestDto.getCategoryId());
        if(fmember.isPresent() && fcategory.isPresent()){
            Board board = Board.CreateBoard(fmember.get(), fcategory.get());
            //파일 저장
            List<BoardDto.Photo> photoList = parseFileInfo(files);
            if(!photoList.isEmpty()) {
                int sort = 1;
                for(BoardDto.Photo photo : photoList) {
                    // 파일을 DB에 저장
                    BoardFile boardFile = new BoardFile();
                    boardFile.setFilePath(photo.getFilePath());
                    boardFile.setFileName(photo.getOrigFileName());
                    boardFile.setSort(sort);
                    boardFile.setReg_date(LocalDateTime.now());
                    board.addFile(fileRepository.save(boardFile));
                    sort++;
                }
            }
            //태그 저장
            String[] split = boardRequestDto.getTagList().split(",");
                for(String item : split) {
                    Tag tag;
                    //등록된 태그가 없으면 저장.
                    Optional<Tag> ftag = tagRepository.findByTagName(item);
                    tag = ftag.orElseGet(() -> tagRepository.save(new Tag(item)));
                    //boardTag 중간 테이블 저장
                    BoardTag boardTag = new BoardTag();
                    boardTag.setTag(tag);
                    board.addTag(boardTag);
                }

                boardRepository.save(board);
        }else{
            throw new ForbiddenException("정보를 찾을 수 없습니다.");
        }

    }


    public List<BoardDto.Photo> parseFileInfo(
            List<MultipartFile> multipartFiles
    ) throws IOException {
        // 반환할 파일 리스트
        List<BoardDto.Photo> fileList = new ArrayList<>();

        // 전달되어 온 파일이 존재할 경우
        if(!CollectionUtils.isEmpty(multipartFiles)) {
            // 파일명을 업로드 한 날짜로 변환하여 저장
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter =
                    DateTimeFormatter.ofPattern("yyyyMMdd");
            String current_date = now.format(dateTimeFormatter);

            // 프로젝트 디렉터리 내의 저장을 위한 절대 경로 설정
            // 경로 구분자 File.separator 사용
            String absolutePath = "C:"+ File.separator + "Users"+ File.separator +"LG"+ File.separator + "OneDrive"+ File.separator +
            "문서"+ File.separator +"codecamp"+ File.separator +"womenSnap"+ File.separator +"public"+ File.separator;

            // 파일을 저장할 세부 경로 지정
            String path = "images" + File.separator + current_date;
            File file = new File(path);

            // 디렉터리가 존재하지 않을 경우
            if(!file.exists()) {
                boolean wasSuccessful = file.mkdirs();

                // 디렉터리 생성에 실패했을 경우
                if(!wasSuccessful)
                   throw new GeneralException("파일 업로드에 실패했습니다.");
            }

            // 다중 파일 처리
            for(MultipartFile multipartFile : multipartFiles) {

                // 파일의 확장자 추출
                String originalFileExtension;
                String contentType = multipartFile.getContentType();

                // 확장자명이 존재하지 않을 경우 처리 x
                if(ObjectUtils.isEmpty(contentType)) {
                    break;
                }
                else {  // 확장자가 jpeg, png인 파일들만 받아서 처리
                    if(contentType.contains("image/jpeg"))
                        originalFileExtension = ".jpg";
                    else if(contentType.contains("image/png"))
                        originalFileExtension = ".png";
                    else  // 다른 확장자일 경우 처리 x
                        break;
                }

                // 파일명 중복 피하고자 나노초까지 얻어와 지정
                String new_file_name = System.nanoTime() + originalFileExtension;


                BoardDto.Photo photo = new BoardDto.Photo();

                photo.setOrigFileName(multipartFile.getOriginalFilename());
                photo.setFilePath(path + File.separator + new_file_name);

                // 생성 후 리스트에 추가
                fileList.add(photo);

                // 업로드 한 파일 데이터를 지정한 파일에 저장
                file = new File(absolutePath + path + File.separator + new_file_name);
                multipartFile.transferTo(file);

                // 파일 권한 설정(쓰기, 읽기)
                file.setWritable(true);
                file.setReadable(true);
            }
        }

        return fileList;
    }

    public List<UserBoardResponse> getUserBoardList(String type, String mem_email){
        //타입 WRITE, LIKE
        if(type.equals("WRITE")){
            return boardRepository.findMemberBoard(mem_email,10L);
        }else{
            return boardRepository.findMemberLike(mem_email,10L);
        }
    }

    public List<BoardListResponse> getBoardList(String tagId, String mem_email){
        Long[] intArray = new Long[0];
        if(StringUtils.hasText(tagId)){
            intArray = Arrays.stream(tagId.split(","))
                    .mapToLong(Integer::parseInt).boxed().toArray(Long[]::new);
        }
        return boardRepository.getBoardList(intArray,mem_email);
    }

}
