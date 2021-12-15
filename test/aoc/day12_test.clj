(ns aoc.day12-test
  (:require [clojure.test :refer :all]
            [aoc.core :as aoc]
            [aoc.day12 :as sut]))

(def test-data (clojure.string/split-lines "start-A
start-b
A-c
A-b
b-d
A-end
b-end"))


(deftest create-cave-map-test
  (let [lines    test-data
        cave-map (sut/create-cave-map lines)]
    (is (= #{"A" "b" "c" "d" "start" "end"}
           (:names cave-map)))
    (are [con] (some #(= con %) (:connections cave-map))
      {:start "start" :end "b"}
      {:start "A" :end "end"})))

(deftest find-path-test
  (let [lines    test-data
        cave-map (sut/create-cave-map lines)
        paths    (sut/find-paths cave-map "start" :already-visited #{})]
    (is (= 10 (count paths)))))
