package com.innocamp.sanmolong.badge.service;

import com.innocamp.sanmolong.badge.dto.BadgeCountResponseDto;
import com.innocamp.sanmolong.badge.dto.BadgeMountainRequestDto;
import com.innocamp.sanmolong.badge.dto.BadgeRequestDto;
import com.innocamp.sanmolong.badge.dto.BadgeResponseDto;
import com.innocamp.sanmolong.badge.entity.Badge;
import com.innocamp.sanmolong.badge.repository.BadgeRepository;
import com.innocamp.sanmolong.mountain.entity.Mountain;
import com.innocamp.sanmolong.mountain.repository.MountainRepository;
import com.innocamp.sanmolong.mountain.service.MountainService;
import com.innocamp.sanmolong.user.entity.User;
import com.innocamp.sanmolong.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BadgeService {
    private final BadgeRepository badgeRepository;
    private final UserService userService;
    private final MountainService mountainService;
    private final MountainRepository mountainRepository;

    // 산별 모든 뱃지 가져오기
    public List<BadgeResponseDto> getBadges(BadgeMountainRequestDto requestDto) {
        String nickname = requestDto.getNickname();
        String mountainNM = requestDto.getMountain();

        User user = userService.findUser(nickname);
        List<Mountain> mountains = mountainRepository.findAllByMountain(mountainNM);

        List<BadgeResponseDto> badges = new ArrayList<>();
        for(int i = 0; i < mountains.size(); i++) {
            Mountain mountain = mountains.get(i);
            Badge badge = badgeRepository.findByUserAndMountain(user, mountain);
            BadgeResponseDto badgeResponseDto = new BadgeResponseDto(badge);
            badges.add(badgeResponseDto);
        }

        return badges;
    }

    public List<BadgeResponseDto> getBadgesToday(BadgeMountainRequestDto requestDto) {
        String nickname = requestDto.getNickname();
        String mountainNM = requestDto.getMountain();

        User user = userService.findUser(nickname);
        List<Mountain> mountains = mountainRepository.findAllByMountain(mountainNM);

        List<BadgeResponseDto> badges = new ArrayList<>();
        for(int i = 0; i < mountains.size(); i++) {
            Mountain mountain = mountains.get(i);
            Badge badge = badgeRepository.findByUserAndMountain(user, mountain);
            String customLocalDateTimeFormat = badge.getGetDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            if(customLocalDateTimeFormat.equals(LocalDate.now().toString())){
                BadgeResponseDto badgeResponseDto = new BadgeResponseDto(badge);
                badges.add(badgeResponseDto);
            }
        }

        return badges;
    }

    public List<BadgeCountResponseDto> getBadgesCount(String nickname) {
        User user = userService.findUser(nickname);
        List<Mountain> mountains = mountainRepository.findAll();

        List<BadgeCountResponseDto> countList = new ArrayList<>();
        countList.add(new BadgeCountResponseDto("설악산", 0));
        countList.add(new BadgeCountResponseDto("치악산", 0));
        countList.add(new BadgeCountResponseDto("오대산", 0));
        countList.add(new BadgeCountResponseDto("태백산", 0));

        for(int i = 0; i < mountains.size(); i++) {
            Mountain mountain = mountains.get(i);
            Badge badge = badgeRepository.findByUserAndMountain(user, mountain);
            if(badge.isCheckBadge()){
                for(int j = 0; j < countList.size(); j++){
                    if(badge.getMountain().getMountain().equals(countList.get(j).getMountain())){
                        countList.get(j).setCount(countList.get(j).getCount()+1);
                    }
                }
            }

        }
        return countList;
    }

    @Transactional
    public boolean createBadge(BadgeRequestDto requestDto) {
        User user = userService.findUser(requestDto.getNickname());
        Mountain mountain = mountainService.findMountainStart(requestDto.getMountain(), requestDto.getCourse(), requestDto.getDepartNm());
        // 사용자 해당 위치 확인
        double distanceKiloMeter = distance(requestDto.getUserLatitude(), requestDto.getUserLongitude(),
                mountain.getStartLatitude(), mountain.getStartLongitude(),"kilometer");

        if(distanceKiloMeter <= 1){
            Badge badge = badgeRepository.findByUserAndMountain(user, mountain);
            badge.update(true);
            return true;
        }else{
            return false;
        }
    }

    /**
     * 두 지점간의 거리 계산
     *
     * @param lat1 지점 1 위도
     * @param lon1 지점 1 경도
     * @param lat2 지점 2 위도
     * @param lon2 지점 2 경도
     * @param unit 거리 표출단위
     * @return
     */
    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        if (unit == "kilometer") {
            dist = dist * 1.609344;
        } else if(unit == "meter"){
            dist = dist * 1609.344;
        }

        return (dist);
    }


    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}
