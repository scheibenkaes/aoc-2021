(ns aoc.day17-test
  (:require [clojure.test :refer :all]
            [aoc.core :as aoc]
            [aoc.day17 :as sut]))

(def test-data (first (clojure.string/split-lines "target area: x=20..30, y=-10..-5")))

(deftest load-s-test
  (let [area (sut/load-target-area test-data)]
    (is (sut/in-target-area? area [30 -10]))
    (is (not (sut/overshot? area [30 -1])))))

(deftest trajectory-test
  (let [probe     (sut/->probe [6 3])
        positions (iterate sut/trajectory probe)
        steps     (take 9 positions)]
    (is (nil? steps))))

(deftest calc-path-test
  (let [path (sut/calc-path (sut/load-target-area test-data) (sut/->probe [7 2]))]
    (is (= 8 (count path)))))
