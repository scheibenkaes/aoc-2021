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
        paths    (sut/find-paths cave-map "start" :already-visited #{})
        ]
    (is (= 10 (count paths)))))

(def test-data2 (clojure.string/split-lines
"fs-end
he-DX
fs-he
start-DX
pj-DX
end-zg
zg-sl
zg-pj
pj-he
RW-he
fs-DX
pj-RW
zg-RW
start-pj
he-WI
zg-he
pj-fs
start-RW"))

(deftest find-path2-test
  (let [lines    test-data2
        cave-map (sut/create-cave-map lines)
        paths    (sut/find-paths cave-map "start" :already-visited #{})]
    (is (= 226 (count paths)))))

(deftest file-test
  (let [lines    (aoc/load-input 12)
        cave-map (sut/create-cave-map lines)
        paths    (sut/find-paths cave-map "start" :already-visited #{})]
    (is (= 4754 (count paths)))))
