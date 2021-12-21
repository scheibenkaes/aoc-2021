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

(deftest run-test
  (let [area         (sut/load-target-area test-data)
        result       (sut/run area)
        result       (reduce (fn [acc e]
                               (let [max-y (apply max (->> e
                                                           (map :position)
                                                           (map second)))]
                                 (update acc max-y conj e))) {} result)
        result       (last (sort-by first result))
        [max-y traj] result]
    (is (= 45 max-y))
    (is (some #(= [6 9] ((juxt :x-vel :y-vel) %)) (map first traj)))))

(deftest run-file-test
  (let [area         (sut/load-target-area (first (aoc/load-input 17)))
        result       (sut/run area)
        result       (reduce (fn [acc e]
                               (let [max-y (apply max (->> e
                                                           (map :position)
                                                           (map second)))]
                                 (update acc max-y conj e))) {} result)
        result       (last (sort-by first result))
        [max-y traj] result]
    (is (= 8646 max-y))))
