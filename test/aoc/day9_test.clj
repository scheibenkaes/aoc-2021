(ns aoc.day9-test
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
    (is (= 15 (sut/risk-level hmap)))))

(deftest ^:integration file-test
  (let [data (-> (aoc/load-input 9)
                 (sut/load-from-lines))
        hmap (sut/calc-low-points data)]
    (is (= 554 (sut/risk-level hmap)))))

(def hmap* (sut/calc-low-points height-map))

(deftest basin-test
  (let [basin1 (sut/basin height-map #{} [1 0])
        basin2 (sut/basin height-map #{} [9 0])
        basin3 (sut/basin height-map #{} [2 2])
        basin4 (sut/basin height-map #{} [6 4])]

    (is (= #{[0 0] [1 0] [0 1]} basin1))
    (is (= 3 (count basin1)))
    (is (= 9 (count basin2)))
    (is (= 14 (count basin3)))
    (is (= 9 (count basin4)))))

(defn top3
  ""
  [basins]
  (->> (into {} (for [b basins] [b (count b)]))
                    (sort-by val >)
                    (take 3)
                    (map val)))


(deftest pt2-test
  (let [lows   (sut/low-points hmap*)
        points (map :x-y lows)
        basins (map (fn [p] (sut/basin height-map #{} p)) points)
        top3   (->> (into {} (for [b basins] [b (count b)]))
                    (sort-by val >)
                    (take 3)
                    (map val))]
    (is (= 4 (count lows)))
    (is (= #{[1 0] [9 0] [2 2] [6 4]}
           (set points)))
    (is (= top3
           '(14 9 9)))
    (is (= 1134 (apply * top3)))))

#_(deftest ^:integration file-pt2-test

  (let [data   (-> (aoc/load-input 9)
                   (sut/load-from-lines))
        lps    (sut/calc-low-points data)
        lows   (sut/low-points lps)
        points (map :x-y lows)
        basins (pmap (fn [p] (sut/basin data #{} p)) points)
        top3   (top3 basins)]

    (is (= 1134 (apply * top3)))))
