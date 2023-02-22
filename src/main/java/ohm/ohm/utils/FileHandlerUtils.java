package ohm.ohm.utils;

import ohm.ohm.entity.Gym.Gym;
import ohm.ohm.entity.Gym.GymImg;
import ohm.ohm.entity.Post.Post;
import ohm.ohm.entity.Post.PostImg;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileHandlerUtils {

    public void delete_file(
            String filePath
    ) throws Exception {

        File file = new File(filePath);
        file.delete();

    }


    public List<GymImg> gymimg_parseFileInfo(
            Gym gym,
            List<MultipartFile> multipartFiles
    ) throws IOException {
        // 반환할 파일 리스트
        List<GymImg> fileList = new ArrayList<>();




        if (multipartFiles.isEmpty()) {
            return fileList;
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter =
                DateTimeFormatter.ofPattern("yyyyMMdd");
        String current_date = now.format(dateTimeFormatter);


        String absolutePath = new File("").getAbsolutePath() + File.separator + File.separator;

        // 파일을 저장할 세부 경로 지정
        String path = "Users/sinminseok12/Desktop/ohmimage/images" + File.separator + current_date;


        File file = new File(path);


        // 디렉터리가 존재하지 않을 경우
        if (!file.exists()) {
            boolean wasSuccessful = file.mkdirs();

            // 디렉터리 생성에 실패했을 경우
            if (!wasSuccessful)
                System.out.println("file: was not successful");
        }

        // 다중 파일 처리
        for (MultipartFile multipartFile : multipartFiles) {
            String ext = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
            String new_file_name = System.nanoTime() + ext;

            // 파일 DTO 생성
            GymImg gymImg = GymImg.builder()
                    .gym(gym)
                    .origFileName(multipartFile.getOriginalFilename())
                    .filePath(path + File.separator + new_file_name)
                    .build();


            // 생성 후 리스트에 추가
            fileList.add(gymImg);

            // 업로드 한 파일 데이터를 지정한 파일에 저장
            file = new File(absolutePath + path + File.separator + new_file_name);

            multipartFile.transferTo(file);

            // 파일 권한 설정(쓰기, 읽기)
            file.setWritable(true);
            file.setReadable(true);

        }
        return fileList;
    }


    public List<PostImg> postimg_parseFileInfo(
            Post post,
            List<MultipartFile> multipartFiles
    ) throws Exception {

        // 반환할 파일 리스트
        List<PostImg> fileList = new ArrayList<>();


        if (multipartFiles.isEmpty()) {
            return fileList;
        }
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter =
                DateTimeFormatter.ofPattern("yyyyMMdd");
        String current_date = now.format(dateTimeFormatter);


        String absolutePath = new File("").getAbsolutePath() + File.separator + File.separator;

        // 파일을 저장할 세부 경로 지정
        String path = "Users/sinminseok12/Desktop/ohmimage/images" + File.separator + current_date;

        File file = new File(path);


        // 디렉터리가 존재하지 않을 경우
        if (!file.exists()) {
            boolean wasSuccessful = file.mkdirs();

            // 디렉터리 생성에 실패했을 경우
            if (!wasSuccessful)
                System.out.println("file: was not successful");
        }

        // 다중 파일 처리
        for (MultipartFile multipartFile : multipartFiles) {


            String ext = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
            ; // 파일 확장자
            // 파일명 중복 피하고자 나노초까지 얻어와 지정
            String new_file_name = System.nanoTime() + ext;

            // 파일 DTO 생성
            PostImg postImg = PostImg.builder()
                    .post(post)
                    .origFileName(multipartFile.getOriginalFilename())
                    .filePath(path + File.separator + new_file_name)
                    .build();


            // 생성 후 리스트에 추가
            fileList.add(postImg);

            // 업로드 한 파일 데이터를 지정한 파일에 저장
            file = new File(absolutePath + path + File.separator + new_file_name);
            multipartFile.transferTo(file);

            // 파일 권한 설정(쓰기, 읽기)
            file.setWritable(true);
            file.setReadable(true);


        }


        return fileList;
    }


    public String profileimg_parseFileInfo(
            MultipartFile multipartFile
    ) throws Exception {
        String fileList = null;

        if (multipartFile.isEmpty()) {
            return fileList;
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter =
                DateTimeFormatter.ofPattern("yyyyMMdd");
        String current_date = now.format(dateTimeFormatter);
        String absolutePath = new File("").getAbsolutePath() + File.separator + File.separator;

        // 파일을 저장할 세부 경로 지정
        String path = "Users/sinminseok12/Desktop/ohmimage/images" + File.separator + current_date;

        File file = new File(path);


        // 디렉터리가 존재하지 않을 경우
        if (!file.exists()) {
            boolean wasSuccessful = file.mkdirs();

            // 디렉터리 생성에 실패했을 경우
            if (!wasSuccessful)
                System.out.println("file: was not successful");
        }

        String ext = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        ; // 파일 확장자
        // 파일명 중복 피하고자 나노초까지 얻어와 지정
        String new_file_name = System.nanoTime() + ext;


        // 업로드 한 파일 데이터를 지정한 파일에 저장
        file = new File(absolutePath + path + File.separator + new_file_name);
        multipartFile.transferTo(file);

        // 파일 권한 설정(쓰기, 읽기)
        file.setWritable(true);
        file.setReadable(true);

        return path + File.separator + new_file_name;

    }
}