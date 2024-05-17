//package com.spring.portfolio.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class RoomService {
//    private final MemberService memberService;
//    private final RoomRepository roomRepository;
//
//    public Long createRoom(long receiverId, MemberDetails memberDetails) {
//        Member receiver = memberService.validateVerifyMember(receiverId);
//        Member sender = memberService.validateVerifyMember(memberDetails.getMemberId());
//
//        ChatRoom chatRoom =
//                ChatRoom
//                        .builder()
//                        .sender(sender)
//                        .receiver(receiver)
//                        .build();
//
//        ChatRoom saveChatRoom = roomRepository.save(chatRoom);
//
//        return saveChatRoom.getRoomId();
//    }
//
//    public ChatRoom findRoom(long roomId) {
//        ChatRoom chatRoom = findExistRoom(roomId);
//
//        return chatRoom;
//    }
//}
