package com.innocamp.sanmolong.chat.service;

import com.innocamp.sanmolong.chat.entity.Chat;
import com.innocamp.sanmolong.chat.entity.Room;
import com.innocamp.sanmolong.chat.repository.ChatRepository;
import com.innocamp.sanmolong.chat.repository.RoomRepository;
import com.innocamp.sanmolong.user.entity.User;
import com.innocamp.sanmolong.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final RoomRepository roomRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    /**
     * 모든 채팅방 찾기
     */
    public List<Room> findAllRoom() {
        return roomRepository.findAll();
    }

    /**
     * 특정 채팅방 찾기
     * @param id room_id
     */
    public Room findRoomById(Long id) {
        return roomRepository.findById(id).orElseThrow();
    }

    /**
     * 채팅방 만들기
     * @param name 방 이름
     */
    public Room createRoom(String name) {
        return roomRepository.save(Room.createRoom(name));
    }

    /////////////////

    /**
     * 채팅 생성
     * @param roomId 채팅방 id
     * @param sender 보낸이
     * @param message 내용
     */
    public Chat createChat(Long roomId, String sender, String message) {
        Room room = roomRepository.findById(roomId).orElseThrow();  //방 찾기 -> 없는 방일 경우 여기서 예외처리
        User user = userRepository.findByNickname(sender).orElseThrow();
        return chatRepository.save(Chat.createChat(room, user, message));
    }

    /**
     * 채팅방 채팅내용 불러오기
     * @param roomId 채팅방 id
     */
    public List<Chat> findAllChatByRoomId(Long roomId) {
        return chatRepository.findAllByRoomId(roomId);
    }
}
