package com.example.hotel_management.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "HOTEL_CAPACITY_BOOKED")
public class BookedCapacity {

    @Id
    @Column(name = "hotel_id")
    private String hotelID;

    @OneToOne
    @JoinColumn(name = "hotel_id",
            insertable = false,
            updatable = false)
    private Hotel hotelBookedCapacity;

    @Column(name = "day1")
    private int day1;

    @Column(name = "day2")
    private int day2;

    @Column(name = "day3")
    private int day3;

    @Column(name = "day4")
    private int day4;

    @Column(name = "day5")
    private int day5;

    @Column(name = "day6")
    private int day6;

    @Column(name = "day7")
    private int day7;

    @Column(name = "day8")
    private int day8;

    @Column(name = "day9")
    private int day9;

    @Column(name = "day10")
    private int day10;

    @Column(name = "day11")
    private int day11;

    @Column(name = "day12")
    private int day12;

    @Column(name = "day13")
    private int day13;

    @Column(name = "day14")
    private int day14;

    @Column(name = "day15")
    private int day15;

    @Column(name = "day16")
    private int day16;

    @Column(name = "day17")
    private int day17;

    @Column(name = "day18")
    private int day18;

    @Column(name = "day19")
    private int day19;

    @Column(name = "day20")
    private int day20;

    @Column(name = "day21")
    private int day21;

    @Column(name = "day22")
    private int day22;

    @Column(name = "day23")
    private int day23;

    @Column(name = "day24")
    private int day24;

    @Column(name = "day25")
    private int day25;

    @Column(name = "day26")
    private int day26;

    @Column(name = "day27")
    private int day27;

    @Column(name = "day28")
    private int day28;

    @Column(name = "day29")
    private int day29;

    @Column(name = "day30")
    private int day30;

    /**
     * Constructor
     * @param hotelID: String
     * @param day1: Int
     * @param day2: Int
     * @param day3: Int
     * @param day4: Int
     * @param day5: Int
     * @param day6: Int
     * @param day7: Int
     * @param day8: Int
     * @param day9: Int
     * @param day10: Int
     * @param day11: Int
     * @param day12: Int
     * @param day13: Int
     * @param day14: Int
     * @param day15: Int
     * @param day16: Int
     * @param day17: Int
     * @param day18: Int
     * @param day19: Int
     * @param day20: Int
     * @param day21: Int
     * @param day22: Int
     * @param day23: Int
     * @param day24: Int
     * @param day25: Int
     * @param day26: Int
     * @param day27: Int
     * @param day28: Int
     * @param day29: Int
     * @param day30: Int
     */
    public BookedCapacity(String hotelID,
                          int day1,
                          int day2,
                          int day3,
                          int day4,
                          int day5,
                          int day6,
                          int day7,
                          int day8,
                          int day9,
                          int day10,
                          int day11,
                          int day12,
                          int day13,
                          int day14,
                          int day15,
                          int day16,
                          int day17,
                          int day18,
                          int day19,
                          int day20,
                          int day21,
                          int day22,
                          int day23,
                          int day24,
                          int day25,
                          int day26,
                          int day27,
                          int day28,
                          int day29,
                          int day30) {
        this.hotelID = hotelID;
        this.day1 = day1;
        this.day2 = day2;
        this.day3 = day3;
        this.day4 = day4;
        this.day5 = day5;
        this.day6 = day6;
        this.day7 = day7;
        this.day8 = day8;
        this.day9 = day9;
        this.day10 = day10;
        this.day11 = day11;
        this.day12 = day12;
        this.day13 = day13;
        this.day14 = day14;
        this.day15 = day15;
        this.day16 = day16;
        this.day17 = day17;
        this.day18 = day18;
        this.day19 = day19;
        this.day20 = day20;
        this.day21 = day21;
        this.day22 = day22;
        this.day23 = day23;
        this.day24 = day24;
        this.day25 = day25;
        this.day26 = day26;
        this.day27 = day27;
        this.day28 = day28;
        this.day29 = day29;
        this.day30 = day30;
    }

    public BookedCapacity() {
    }

    /**
     * Convert to list
     * @return
     * A List capacity for specific day after
     */
    public List<Object> toList(){
        List<Object> result = new ArrayList<>();
        result.add(this.day1);
        result.add(this.day2);
        result.add(this.day3);
        result.add(this.day4);
        result.add(this.day5);
        result.add(this.day6);
        result.add(this.day7);
        result.add(this.day8);
        result.add(this.day9);
        result.add(this.day10);
        result.add(this.day11);
        result.add(this.day12);
        result.add(this.day13);
        result.add(this.day14);
        result.add(this.day15);
        result.add(this.day16);
        result.add(this.day17);
        result.add(this.day18);
        result.add(this.day19);
        result.add(this.day20);
        result.add(this.day21);
        result.add(this.day22);
        result.add(this.day23);
        result.add(this.day24);
        result.add(this.day25);
        result.add(this.day26);
        result.add(this.day27);
        result.add(this.day28);
        result.add(this.day29);
        result.add(this.day30);

        return new ArrayList<>(result);
    }
}
