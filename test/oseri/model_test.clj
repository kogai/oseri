(ns oseri.model-test
  (:use
   oseri.model
   oseri.view)
  (:require [clojure.test :refer :all]
            [oseri.core :refer :all]))

(deftest test-successor
  (testing "should derive successor"
    (is (= (successor :N {:row 1 :col 1}) {:row 0 :col 1}))
    (is (= (successor :NE {:row 1 :col 1}) {:row 0 :col 2}))
    (is (= (successor :E {:row 1 :col 1}) {:row 1 :col 2}))
    (is (= (successor :SE {:row 1 :col 1}) {:row 2 :col 2}))
    (is (= (successor :S {:row 1 :col 1}) {:row 2 :col 1}))
    (is (= (successor :SW {:row 1 :col 1}) {:row 2 :col 0}))
    (is (= (successor :W {:row 1 :col 1}) {:row 1 :col 0}))
    (is (= (successor :NW {:row 1 :col 1}) {:row 0 :col 0}))))

(deftest test-wrapped?
  (testing "should be detection wrapped"
    (let [b (board 2)]
      (is (= true (wrapped? {:row 1 :col 1} b)))
      (is (= false (wrapped? {:row 2 :col 2} b)))
      (is (= false (wrapped? {:row -1 :col 0} b)))
)))

(deftest test-get-tile
  (testing "should get tile"
    (let [b (board 2)](is (= (get-tile {:row 1 :col 1} b) {:row 1 :col 1 :color :empty })))))

; (deftest test-correct-line
;   (testing "should correct line"
;     (let [board '(({:color :empty :row 0 :col 0} {:color :black :row 0 :col 1} {:color :white :row 0 :col 2})
;                   ({:color :empty :row 1 :col 0} {:color :black :row 1 :col 1} {:color :white :row 1 :col 2})
;                   ({:color :empty :row 2 :col 0} {:color :black :row 2 :col 1} {:color :white :row 2 :col 2}))]
;       (is (= (correct-line :N {:row 2 :col 1} board) '({:color :black :row 2 :col 1} {:color :black :row 1 :col 1} {:color :black :row 0 :col 1})))
;       (is (= (correct-line :SW {:row 0 :col 2} board) '({:color :white :row 0 :col 2} {:color :black :row 1 :col 1} {:color :empty :row 2 :col 0})))
;       (is (= (correct-line :E {:row 1 :col 0} board) '({:color :empty :row 1 :col 0} {:color :black :row 1 :col 1} {:color :white :row 1 :col 2}))))))
