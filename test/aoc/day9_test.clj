(ns aoc.day6-test
  (:require [clojure.test :refer :all]
            [aoc.core :as aoc]
            [aoc.day9 :as sut]))

(def test-data "
2199943210
3987894921
9856789892
8767896789
9899965678")


(def height-map (sut/load-from-string test-data))


(deftest low-point-at-test
  (let [{:keys [self top-n bottom-n right-n left-n low-point]} (sut/low-point-at height-map [1 0])]
    (is (= top-n nil))
    (is (= self 1))
    (is (= bottom-n 9))
    (is (= left-n 2))
    (is (= right-n 9))
    (is (true? low-point))))

(deftest low-point-map-test
  (let [hmap     (sut/calc-low-points height-map)
        l-points (filter :low-point hmap)]
    (is (= 4 (count l-points)))
    (is (= [1 0 5 5] (mapv :self l-points)))
    (is (= [2 1 6 6] (mapv :risk l-points)))
    (is (= 15 (risk-level hmap)))))

(deftest ^:integration file-test
  (let [data (-> (aoc/load-input 9)
                 (sut/load-from-lines))
        hmap (sut/calc-low-points data)]
    (is (= 554 (sut/risk-level hmap)))))
