package com.spring.portfolio.service;

import com.spring.portfolio.dto.MemberRequestDto;
import com.spring.portfolio.entity.Member;
import com.spring.portfolio.store.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // 임시 비밀번호 생성
    private String generateTempPassword() {
        String chars1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String chars2 = "abcdefghijklmnopqrstuvwxyz";
        String chars3 = "!@#$%^&*";
        String chars4 = "0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            password.append(chars1.charAt(random.nextInt(chars1.length())));
            password.append(chars2.charAt(random.nextInt(chars2.length())));
            password.append(chars4.charAt(random.nextInt(chars4.length())));
        }
        password.append(chars3.charAt(random.nextInt(chars3.length())));

        // 비밀번호 문자 섞기 (랜덤 순서 보장)
        // String.chars() : String을 유니코드(숫자)로 변환
        // mapToObj( 변수 -> (타입) 변수) : 여기서는 위 유니코드를 타입(char 타입)으로 변환한거
        // .collect(Collectors.toList()); : 스트림을 List로 변환
        // Collections.shuffle() : 섞기
        List<Character> passwordChars = password.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.shuffle(passwordChars);

        return passwordChars.stream()
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    // 비밀번호 재발급 이메일 전송
    @Transactional
    public void sendResetPasswordEmail(String email) {
        Member member = memberRepository.findByMemberMail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일을 찾을 수 없습니다."));

        String tempPassword = generateTempPassword(); // 임시 비밀번호 생성

        member.modifyPw(passwordEncoder.encode(tempPassword)); // 비밀번호 암호화 후 저장 (더티체킹)
//        memberRepository.save(member); // 변경된 비밀번호 저장

        // 이메일 내용 설정
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("[비밀번호 재설정 안내]"); // 이메일 제목
        message.setText("안녕하세요, " + member.getMemberName() + "님.\n\n" +
                "임시 비밀번호: " + tempPassword + "\n\n" +
                "로그인 후 반드시 비밀번호를 변경해주세요."); // 이메일 내용

        mailSender.send(message);
    }

}
