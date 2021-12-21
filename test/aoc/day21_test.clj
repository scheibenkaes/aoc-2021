(ns aoc.day21-test
  (:require [clojure.test :refer :all]
            [aoc.core :as aoc]
            [aoc.day21 :as sut]))

(def test-data (clojure.string/split-lines "Player 1 starting position: 4
Player 2 starting position: 8"))

(deftest load-start-pos-test
  (let [pos (sut/load-starting-pos test-data)]
    (is (nil? pos))))
