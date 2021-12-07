(ns aoc.day4-test
  (:require [clojure.test :refer :all]
            [aoc.core :as aoc]
            [aoc.day4 :as sut]))


(def test-data "7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1

22 13 17 11  0
 8  2 23  4 24
21  9 14 16  7
 6 10  3 18  5
 1 12 20 15 19

 3 15  0  2 22
 9 18 13 17  5
19  8  7 25 23
20 11 10 24  4
14 21 16 12  6

14 21 17 24  4
10 16 15  9 19
18  8 23 26 20
22 11 13  6  5
 2  0 12  3  7")

(def test-board
  "22 13 17 11  0
 8  2 23  4 24
21  9 14 16  7
 6 10  3 18  5
 1 12 20 15 19")


(def test-file (aoc/load-input 4))


(comment
  (deftest oxgen-test
    (is (= 23 (sut/oxygen test-input))))

  (deftest co2-test
    (is (= 10 (sut/co2 test-input))))

  (deftest epsilon-test
    (testing "with same number of bits returns 0"
      (is (= (sut/epsilon "10") (sut/epsilon "010101") \0))))

  (deftest gamma-test
    (testing "with same number of bits returns 1"
      (is (= (sut/gamma "10") (sut/gamma "101010") \1))))

  (deftest solve-test
    (testing "test input leads to output"
      (let [{gamma :gamma epsilon :epsilon bits :bits mult :mult life-support :life-support} (sut/solve test-input)]
        (is (not (nil? bits)))
        (is (= gamma 22))
        (is (= epsilon 9))
        (is (= mult 198))
        (is (= life-support 230)))))

  (deftest ^:integration solve-on-input-file
    (let [result (sut/run-on-test-data!)]
      (is (= 3882564 (:mult result)))
      (is (= 3385170 (:life-support result))))))
