package com.innocamp.sanmolong.chat.repository;

import com.innocamp.sanmolong.chat.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
