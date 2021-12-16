(ns aoc.day13-test
  (:require [clojure.test :refer :all]
            [aoc.core :as aoc]
            [aoc.day13 :as sut]))

(def test-data (clojure.string/split-lines "6,10
0,14
9,10
0,3
10,4
4,11
6,0
6,12
4,1
0,13
10,12
3,4
3,0
8,4
1,10
2,14
8,10
9,0

fold along y=7
fold along x=5"))


(def test-paper (sut/load test-data))

(defn print-set-map
  [dmap]
  (print
   (with-out-str
     (let [max-x (inc (apply max (map first dmap)))
           max-y (inc (apply max (map second dmap)))]
       (doseq [iy (range max-y)]
         (doseq [ix (range max-x)
                 v  (if (get dmap [ix iy]) "#" ".")]
           (print v " "))
         (println))))))

(deftest fold-test
  (let [{coords :coords [ffold sfold] :folds} test-paper

        folded  (sut/fold coords ffold)
        folded2 (sut/fold folded sfold)]
    (is (= 17 (count folded)))
    (is (= 16 (count folded2)))
    (print-set-map folded2)))

(deftest file-test
  (let [{:keys [coords folds]} (sut/load (aoc/load-input 13))
        f1                     (sut/fold coords (first folds))]
    (is (= 755
           (count f1)))
    (loop [coords* coords
           folds*  folds
           n       1]
      (when-not (empty? folds*)
        (let [folded (sut/fold coords* (first folds*))]
          (println "----------------------->" n)
          (print-set-map folded)
          (println "<-----------------------" n)
          (recur folded (rest folds*) (inc n)))))))
