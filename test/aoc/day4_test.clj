(ns aoc.day4-test
  (:require [clojure.test :refer :all]
            [aoc.core :as aoc]
            [aoc.day4 :as sut]))


(def test-data (clojure.string/split-lines
"7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1

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
 2  0 12  3  7"))


(def test-file (aoc/load-input 4))

(defn run-on-data
  [data]
  (->>  data
        sut/load-board
        sut/run-numbers
        sut/score-game))

(deftest run-numbers-test
  (let [{boards :boards :as game} (run-on-data test-data)
        winner-board              (sut/winner-board boards)]
    (is (not= nil winner-board))
    (is (= (set (:numbers winner-board))
           #{14 21 17 24  4
             10 16 15  9 19
             18  8 23 26 20
             22 11 13  6  5
             2  0 12  3  7}))
    (is (= (:checked-nums winner-board)
           #{14 21 17 24 4 9 23 11 5 2 0 7}))))

(deftest score-test
  (let [{score :score} (run-on-data test-data)]
    (is (= 4512 score))))

(deftest test-file-test
  (let [game (run-on-data test-file)]
    (is (= (:score game)
           38913))))

(defn run-pt2-on-data
  ""
  [data]
  (->> data
       sut/load-board
       sut/run-pt2
       sut/score-game))

(deftest score-pt2-test
  (let [{score :score} (run-pt2-on-data test-data)]
    (is (= 1924 score))))

(deftest test-pt2-file-test
  (let [game (run-pt2-on-data test-file)]
    (is (= 16836
           (:score game)))))
