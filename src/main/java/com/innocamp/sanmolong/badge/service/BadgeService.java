package com.innocamp.sanmolong.badge.service;

import com.innocamp.sanmolong.badge.dto.*;
import com.innocamp.sanmolong.badge.entity.Badge;
import com.innocamp.sanmolong.badge.repository.BadgeRepository;
import com.innocamp.sanmolong.mountain.entity.Course;
import com.innocamp.sanmolong.mountain.entity.Departure;
import com.innocamp.sanmolong.mountain.entity.Mountain;
import com.innocamp.sanmolong.mountain.repository.CourseRepository;
import com.innocamp.sanmolong.mountain.repository.DepartureRepository;
import com.innocamp.sanmolong.mountain.repository.MountainRepository;
import com.innocamp.sanmolong.mountain.service.MountainService;
import com.innocamp.sanmolong.user.entity.User;
import com.innocamp.sanmolong.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BadgeService {
    private final BadgeRepository badgeRepository;
    private final UserService userService;
    private final MountainRepository mountainRepository;
    private final CourseRepository courseRepository;
    private final DepartureRepository departureRepository;


    // 산별 모든 뱃지 가져오기
    public List<BadgeResponseDto> getBadges(BadgeMountainRequestDto requestDto) {
        String nickname = requestDto.getNickname();
        String mountainNM = requestDto.getMountain();

        User user = userService.findUser(nickname);
        Mountain mountain = mountainRepository.findByMountainNm(mountainNM);
        List<Course> courses = courseRepository.findAllByMountain(mountain);
        List<Departure> departures = getDepart(courses);

        List<BadgeResponseDto> badges = new ArrayList<>();
        for(int i = 0; i < departures.size(); i++) {
            Departure departure = departures.get(i);
            Badge badge = badgeRepository.findByUserAndDeparture(user, departure);
            BadgeResponseDto badgeResponseDto = new BadgeResponseDto(badge);
            badges.add(badgeResponseDto);
        }

        return badges;
    }

    public List<BadgeResponseDto> getBadgesToday(BadgeMountainRequestDto requestDto) {
        String nickname = requestDto.getNickname();
        String mountainNM = requestDto.getMountain();

        User user = userService.findUser(nickname);
        Mountain mountain = mountainRepository.findByMountainNm(mountainNM);
        List<Course> courses = courseRepository.findAllByMountain(mountain);
        List<Departure> departures = getDepart(courses);

        List<BadgeResponseDto> badges = new ArrayList<>();
        for(int i = 0; i < departures.size(); i++) {
            Departure departure = departures.get(i);
            Badge badge = badgeRepository.findByUserAndDeparture(user, departure);
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
        List<Course> courses = courseRepository.findAll();
        List<Departure> departures = getDepart(courses);

        List<BadgeCountResponseDto> countList = new ArrayList<>();
        countList.add(new BadgeCountResponseDto("설악산", 0, null));
        countList.add(new BadgeCountResponseDto("치악산", 0, null));
        countList.add(new BadgeCountResponseDto("오대산", 0, null));
        countList.add(new BadgeCountResponseDto("태백산", 0, null));

        for(int i = 0; i < departures.size(); i++) {
            Departure departure = departures.get(i);
            Badge badge = badgeRepository.findByUserAndDeparture(user, departure);
            if (badge.isCheckBadge()) {
                for (int j = 0; j < countList.size(); j++) {
                    if (badge.getDeparture().getCourse().getMountain().getMountainNm().equals(countList.get(j).getMountainNm())) {
                        countList.get(j).setCount(countList.get(j).getCount() + 1);
                    }
                }
            }
        }
        for(int i = 0; i < countList.size(); i++){
            String latelyDate = getLatelyDate(nickname, countList.get(i).getMountainNm());
            countList.get(i).setLatelyDate(latelyDate);
        }

        return countList;
    }

    // 유저별 산별 가장 최근 날짜 가져오기
    public String getLatelyDate(String nickname, String mountainNM){
        User user = userService.findUser(nickname);
        Mountain mountain = mountainRepository.findByMountainNm(mountainNM);
        List<Course> courses = courseRepository.findAllByMountain(mountain);
        List<Departure> departures = getDepart(courses);

        LocalDateTime initDate = badgeRepository.findByUserAndDeparture(user, departures.get(0)).getGetDate();
        for(int i = 1; i < departures.size(); i++) {
            Departure departure = departures.get(i);
            Badge badge = badgeRepository.findByUserAndDeparture(user, departure);
            if(badge.getGetDate().isAfter(initDate)){
                initDate = badge.getGetDate();
            }
        }

        String latelyDate = initDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        return latelyDate;
    }

    @Transactional
    public boolean createBadge(BadgeRequestDto requestDto) {
        User user = userService.findUser(requestDto.getNickname());
//        Mountain mountain = mountainService.findMountainStart(requestDto.getMountain(), requestDto.getCourse(), requestDto.getDepartNm());

        Departure departure = departureRepository.findByDepartNm(requestDto.getDepartNm());

        // 사용자 해당 위치 확인
        double distanceKiloMeter = distance(requestDto.getUserLatitude(), requestDto.getUserLongitude(),
                departure.getStartLatitude(), departure.getStartLongitude(),"kilometer");

        if(distanceKiloMeter <= 1){
            Badge badge = badgeRepository.findByUserAndDeparture(user, departure);
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

    // 산별 출발지 가져오기
    public List<Departure> getDepart(List<Course> courses){
        List<Departure> departures = new ArrayList<>();
        for(int i = 0; i < courses.size(); i++){
            Course course = courses.get(i);
            List<Departure> departureAtCourse = departureRepository.findAllByCourse(course);
            departureAtCourse.stream().forEach(
                    departure -> departures.add(departure)
            );
        }
        return departures;
    }
}
