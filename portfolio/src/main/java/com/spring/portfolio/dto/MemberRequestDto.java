package com.spring.portfolio.dto;

import com.spring.portfolio.entity.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

// 일반 로그인(세션) dto
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRequestDto {

    private Long memberIdx;

    //    private String memberId;
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String memberPw;

    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
    private String memberName;

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String memberMail;

    private Role memberRole;
    private boolean isLimited;
    private String visible;

    private String introduction;
    private String profileImage;


    @Builder
    public MemberRequestDto(String memberName, Long memberIdx, String introduction, String visible) {
        this.memberIdx = memberIdx;
        this.memberName = memberName;
        this.introduction = introduction;
        this.visible = visible;
    }

    // 아마 안쓰는듯
//    @Builder
//    public MemberRequestDto(String memberName, String memberMail, String profile, Address memberAddress, String detailAddress, String memberPhone, boolean isLimited, String visible) {
//        this.memberPw = memberPw;
//        this.memberName = memberName;
//        this.memberMail = memberMail;
//        this.memberRole = Role.USER;
//        this.isLimited = false;
//        this.visible = "N";
//    }


    // to Entity방식은 return builder.build() 방식이 맞는듯
    public Member toEntity() {
        return Member.builder()
                .memberPw(memberPw)
                .memberName(memberName)
                .memberMail(memberMail)
                .memberRole(memberRole)
                .isLimited(isLimited)
                .visible(visible)
                .build();
    }

}
