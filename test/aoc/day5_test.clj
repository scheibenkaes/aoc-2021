(ns aoc.day5-test
  (:require [clojure.test :refer :all]
            [aoc.core :as aoc]
            [aoc.day5 :as sut]))

(def test-data
  (clojure.string/split-lines
   "0,9 -> 5,9
8,0 -> 0,8
9,4 -> 3,4
2,2 -> 2,1
7,0 -> 7,4
6,4 -> 2,0
0,9 -> 2,9
3,4 -> 1,4
0,0 -> 8,8
5,5 -> 8,2"))

(def test-file (aoc/load-input 5))

(deftest ->line-test
  (let [line (sut/->line "1,1 -> 1,3")]
    (is (= #{[1 1] [1 2] [1 3]}
           (get-in line [:path :path])))))

(deftest weigh-test
  (let [lines (map sut/->line test-data)
        mp    (sut/->line-map (sut/filter-diagonals lines))
        w     (sut/weigh mp)]
    (is (= 5 w))))

(deftest weigh-with-diagonals-test
  (let [lines (map sut/->line test-data)
        mp    (sut/->line-map lines)
        w     (sut/weigh mp)]
    (is (= 12 w))))

(deftest ^:integration weigh-test-file-wo-diagonals-test
  (let [weight (->> test-file (map sut/->line) sut/filter-diagonals sut/->line-map sut/weigh)]
    (is (= 5197 weight))))

(deftest ^:integration weigh-test-file-with-diagonals-test
  (let [weight (->> test-file (map sut/->line) sut/->line-map sut/weigh)]
    (is (= 5197 weight))))
