(ns oseri.model-test
  (:require [clojure.test :refer :all]
            [oseri.core :refer :all]
            [oseri.view :refer :all]
            [oseri.model :refer :all]))

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
      (is (= false (wrapped? {:row -1 :col 0} b))))))

(deftest test-get-tile
  (testing "should get tile"
    (let [b (board 2)] (is (= (get-tile b {:row 1 :col 1}) #oseri.view.Tile {:row 1 :col 1 :color :empty})))))

(deftest test-correct-line
  (testing "should correct line"
    (let [b '(({:color :empty :row 0 :col 0} {:color :black :row 0 :col 1} {:color :white :row 0 :col 2})
              ({:color :empty :row 1 :col 0} {:color :black :row 1 :col 1} {:color :white :row 1 :col 2})
              ({:color :empty :row 2 :col 0} {:color :black :row 2 :col 1} {:color :white :row 2 :col 2}))]
      (is (= (correct-line :N {:row 2 :col 1} b) '({:color :black :row 2 :col 1} {:color :black :row 1 :col 1} {:color :black :row 0 :col 1})))
      (is (= (correct-line :SW {:row 0 :col 2} b) '({:color :white :row 0 :col 2} {:color :black :row 1 :col 1} {:color :empty :row 2 :col 0})))
      (is (= (correct-line :E {:row 1 :col 0} b) '({:color :empty :row 1 :col 0} {:color :black :row 1 :col 1} {:color :white :row 1 :col 2}))))))

(deftest test-clasp?
  (testing "should detect claspable line"
    (let [line-1 [(->Tile 0 0 :white) (->Tile 0 1 :white) (->Tile 0 2 :white) (->Tile 0 3 :empty)]
          line-2 [(->Tile 0 0 :black) (->Tile 0 1 :black) (->Tile 0 2 :black) (->Tile 0 3 :empty)]
          line-3 [(->Tile 0 0 :black) (->Tile 0 1 :black) (->Tile 0 2 :white) (->Tile 0 3 :empty)]]
      (is (= true (clasp? :black line-1)))
      (is (= false (clasp? :black line-2)))
      (is (= false (clasp? :black line-3)))
)))
