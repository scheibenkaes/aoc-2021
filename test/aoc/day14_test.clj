(ns aoc.day14-test
  (:require [clojure.test :refer :all]
            [aoc.core :as aoc]
            [aoc.day14 :as sut]))

(def test-data (clojure.string/split-lines "NNCB

CH -> B
HH -> N
CB -> H
NH -> C
HB -> C
HC -> B
HN -> C
NN -> C
BH -> H
NC -> B
NB -> B
BN -> B
BB -> N
BC -> B
CC -> N
CN -> C"))



(deftest create-insertions-test
  (let [[template insertions] (sut/read-input test-data)
        result                (sut/create-insertions template insertions)]
    (is (= '(\C \B \H)
           result))))

(deftest apply-insertions-test
  (let [[template insertions] (sut/read-input test-data)
        result                (sut/create-insertions template insertions)]
    (is (= '(\N \C \N \B \C \H \B)
           (sut/apply-insertions template result)))))

(def ->char-list seq)

(defn run
  [template funs times]
  {:pre [(>= times 0)]}
  (if (zero? times)
    template
    (let [inserts   (sut/create-insertions template funs)
          template* (sut/apply-insertions template inserts)]
      (recur template* funs (dec times)))))

(deftest run-test
  (let [[template insertions] (sut/read-input test-data)
        result                (run template insertions 1)]
    (is (= '(\N \C \N \B \C \H \B)
           result)))

  (let [[template insertions] (sut/read-input test-data)
        result                (run template insertions 2)]
    (is (= (->char-list "NBCCNBBBCBHCB")
           result)))

  (let [[template insertions] (sut/read-input test-data)
        result                (run template insertions 3)]
    (is (= (->char-list "NBBBCNCCNBBNBNBBCHBHHBCHB")
           result)))

  (let [[template insertions] (sut/read-input test-data)
        result                (run template insertions 4)]
    (is (= (->char-list "NBBNBNBBCCNBCNCCNBBNBBNBBBNBBNBBCBHCBHHNHCBBCBHCB")
           result))))

(defn analyse-result
  [result]
  (let [occurences   (reduce (fn [occs e] (update occs e aoc/inc*)) {} result)
        sorted       (sort-by val occurences)
        least-common (first sorted)
        most-common  (last sorted)]
    {:most-common  (first most-common)
     :least-common (first least-common)
     :count-most   (second most-common)
     :count-least  (second least-common)}))

(deftest run-on-test-data-test
  (let [[template insertions] (sut/read-input test-data)
        result                (run template insertions 10)
        {:keys [most-common
                least-common
                count-most
                count-least]} (analyse-result result)]
    (is (= \H least-common))
    (is (= \B most-common))
    (is (= 1588
           (- count-most count-least)))))

(deftest run-on-test-data-test
  (let [[template insertions] (sut/read-input (aoc/load-input 14))
        result                (run template insertions 10)
        {:keys [count-most
                count-least]} (analyse-result result)]
    (is (= 2068
           (- count-most count-least)))))
