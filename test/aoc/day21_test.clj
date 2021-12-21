(ns aoc.day21-test
  (:require [clojure.test :refer :all]
            [aoc.core :as aoc]
            [aoc.day21 :as sut]))

(def test-data (clojure.string/split-lines "Player 1 starting position: 4
Player 2 starting position: 8"))

(deftest load-start-pos-test
  (let [pos (sut/load-starting-pos test-data)]
    (is (= [4 8] pos))))

(deftest roll!-test
  (let [d (sut/->dice)
        r (sut/roll! d)
        rolls (iterate sut/roll! d)
        rolls (nth rolls 5)

        hundreds (iterate sut/roll! (sut/->dice))
        hundreds (nth hundreds 100)

        and1 (iterate sut/roll! (sut/->dice))
        and1 (nth and1 101)]
    (is (= 1 (:times-rolled r))
        (= 1 (:roll r)))
    (is (= 5 (:times-rolled rolls))
        (= 5 (:roll rolls)))
    (is (= 100 (:times-rolled hundreds))
        (= 100 (:roll hundreds)))
    (is (= 101 (:times-rolled and1))
        (= 1   (:roll and1)))))

(deftest tick-test
  (let [game  (sut/->game test-data)
        game* (sut/tick game)]
    (is (= 10 (:score1 game*)))
    (is (= 10 (:player1 game*)))
    (is (= 3 (:score2 game*)))
    (is (= 3 (:player2 game*)))
    (is (nil? (:winner game*)))

    (let [game* (sut/tick game*)]
      (is (= 14 (:score1 game*)))
      (is (= 4 (:player1 game*)))
      (is (= 9 (:score2 game*)))
      (is (= 6 (:player2 game*)))

      (let [game* (sut/tick game*)]
        (is (= 20 (:score1 game*)))
        (is (= 6 (:player1 game*)))
        (is (= 16 (:score2 game*)))
        (is (= 7 (:player2 game*)))))))

(deftest score-game-test
  (let [game   (sut/->game test-data)
        ticks  (iterate sut/tick game)
        win    (first (filter #(not= nil (:winner %)) ticks))
        result (sut/score-game win)]
    (is (= 739785 result))))

(deftest file-test
  (let [game   (sut/->game (aoc/load-input 21))
        ticks  (iterate sut/tick game)
        win    (first (filter #(not= nil (:winner %)) ticks))
        result (sut/score-game win)]
    (is (= 862110 result))))
