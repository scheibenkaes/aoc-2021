(ns aoc.day3-test
  (:require [clojure.test :refer :all]
            [aoc.day3 :as sut]))

(def test-input ["00100"
                 "11110"
                 "10110"
                 "10111"
                 "10101"
                 "01111"
                 "00111"
                 "11100"
                 "10000"
                 "11001"
                 "00010"
                 "01010"])

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
    (is (= 3385170 (:life-support result)))))
