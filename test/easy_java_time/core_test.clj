(ns easy-java-time.core-test
  (:require [clojure.test :as t :refer [is]]
            [easy-java-time.core :as tt])
  (:import [java.time LocalDateTime Instant LocalDate ZoneOffset]
           [java.time.temporal ChronoUnit]))

;; TODO/COVERAGE:
;; =======================================================
;;
;; "x" = implemented, tested
;; "!" = has special condition (usually noted)
;;
;; 1.  [x] add-inst
;; 2.  [x] add-local-date-time
;; 3.  [x] chrono-unit
;; 4.  [x] get-day-of-month
;; 5.  [x] get-day-of-week
;; 6.  [x] get-day-of-year
;; 7.  [x] get-hour
;; 8.  [x] get-minute
;; 9.  [x] get-month
;; 10. [x] get-month-value
;; 13. [x] get-second
;; 14. [x] get-year
;; 15. [x] inst->local-date-time
;; 16. [ ] inst->timestamp
;; 17. [x] local-date-time->inst
;; 18. [ ] local-date-time->timestamp
;; 19. [x] now
;; 20. [x] str->inst
;; 21. [x] str->local-date-time
;; 22. [x] sub-inst
;; 23. [x] sub-local-date-time

(t/deftest add-inst
  (t/testing "easy-java-time.core/add-isnt"
    (let [i (tt/str->inst "2012-12-12T00:00:00Z")
          i+6years (tt/add-inst i 6 :years)
          i+6years+6months (tt/add-inst i+6years 6 :months)]
      (is (= 2012 (tt/get-year i)))
      (is (= 12 (tt/get-month-value i)))
      (is (= 12 (tt/get-day-of-month i)))
      (is (= 2018 (tt/get-year i+6years)))
      (is (= 6 (tt/get-month-value i+6years+6months)))
      (is (= 12 (tt/get-day-of-month i+6years)))
      (is (= 12 (tt/get-day-of-month i+6years+6months))))))

(t/deftest add-local-date-time
  (t/testing "easy-java-time.core/add-local-date-time"
    (let [d (tt/str->local-date-time "2001-01-01T01:01:01Z")
          d+1year (tt/add-local-date-time d 1 :year)
          d+1year+1month (tt/add-local-date-time d+1year 1 :month)
          d+1year+1month+1hour (tt/add-local-date-time d+1year+1month 1 :hour)]

      (is (= 2001 (tt/get-year d)))
      (is (= 1 (tt/get-month-value d)))
      (is (= 1 (tt/get-hour d)))

      (is (= 2002 (tt/get-year d+1year)))
      (is (= 1 (tt/get-month-value d+1year)))
      (is (= 1 (tt/get-hour d+1year)))

      (is (= 2002 (tt/get-year d+1year+1month)))
      (is (= 2 (tt/get-month-value d+1year+1month)))
      (is (= 1 (tt/get-hour d+1year+1month)))

      (is (= 2002 (tt/get-year d+1year+1month+1hour)))
      (is (= 2 (tt/get-month-value d+1year+1month+1hour)))
      (is (= 2 (tt/get-hour d+1year+1month+1hour))))))

(t/deftest chrono-unit
  (t/testing "easy-java-time.core/chrono-unit"
    (let [possibilites [[:days    ChronoUnit/DAYS]
                        [:day     ChronoUnit/DAYS]
                        [:decades ChronoUnit/DECADES]
                        [:decade  ChronoUnit/DECADES]
                        [:hours   ChronoUnit/HOURS]
                        [:hour    ChronoUnit/HOURS]
                        [:micros  ChronoUnit/MICROS]
                        [:micro   ChronoUnit/MICROS]
                        [:millis  ChronoUnit/MILLIS]
                        [:milli   ChronoUnit/MILLIS]
                        [:minutes ChronoUnit/MINUTES]
                        [:minute  ChronoUnit/MINUTES]
                        [:months  ChronoUnit/MONTHS]
                        [:month   ChronoUnit/MONTHS]
                        [:seconds ChronoUnit/SECONDS]
                        [:second  ChronoUnit/SECONDS]
                        [:weeks   ChronoUnit/WEEKS]
                        [:week    ChronoUnit/WEEKS]
                        [:years   ChronoUnit/YEARS]
                        [:year    ChronoUnit/YEARS]]]
      (doseq [[unit correct-response] possibilites]
        (is (= correct-response (tt/chrono-unit unit)))))))


(t/deftest get-day-of-month
  (t/testing "easy-java-time.core/get-day-of-month"
    (let [i1 (tt/str->inst "2012-12-12T12:12:12Z")
          i2 (tt/str->inst "2006-06-06T06:06:06Z")
          d1 (tt/str->local-date-time "2012-12-12T12:12:12Z")
          d2 (tt/str->local-date-time "2006-06-06T06:06:06Z")]
      (is (= 12 (tt/get-day-of-month i1)))
      (is (= 6 (tt/get-day-of-month i2)))
      (is (= 12 (tt/get-day-of-month d1)))
      (is (= 6 (tt/get-day-of-month d2))))))

(t/deftest get-day-of-week
  (t/testing "easy-java-time.core/get-day-of-week"
    (let [i1 (tt/str->inst "2012-12-12T12:12:12Z")
          i2 (tt/str->inst "2006-06-06T06:06:06Z")
          d1 (tt/str->local-date-time "2012-12-12T12:12:12Z")
          d2 (tt/str->local-date-time "2006-06-06T06:06:06Z")]
      (is (= "WEDNESDAY" (str (tt/get-day-of-week i1))))
      (is (= "TUESDAY" (str (tt/get-day-of-week i2))))
      (is (= "WEDNESDAY" (str (tt/get-day-of-week d1))))
      (is (= "TUESDAY" (str (tt/get-day-of-week d2)))))))

(t/deftest get-day-of-year
  (t/testing "easy-java-time.core/get-day-of-year"
    (let [i1 (tt/str->inst "2012-12-12T12:12:12Z")
          i2 (tt/str->inst "2006-06-06T06:06:06Z")
          d1 (tt/str->local-date-time "2012-12-12T12:12:12Z")
          d2 (tt/str->local-date-time "2006-06-06T06:06:06Z")]
      (is (= 347 (tt/get-day-of-year i1)))
     (is (= 157 (tt/get-day-of-year i2)))
     (is (= 347 (tt/get-day-of-year d1)))
     (is (= 157 (tt/get-day-of-year d2))))))

(t/deftest get-hour
  (t/testing "easy-java-time.core/get-hour"
    (let [i1 (tt/str->inst "2012-12-12T12:12:12Z")
          i2 (tt/str->inst "2006-06-06T06:06:06Z")
          d1 (tt/str->local-date-time "2012-12-12T12:12:12Z")
          d2 (tt/str->local-date-time "2006-06-06T06:06:06Z")]
      (is (= 12 (tt/get-hour i1)))
      (is (= 6 (tt/get-hour i2)))
      (is (= 12 (tt/get-hour d1)))
      (is (= 6 (tt/get-hour d2))))))

(t/deftest get-minute
  (t/testing "easy-java-time.core/get-minute"
    (let [i1 (tt/str->inst "2012-12-12T12:12:12Z")
          i2 (tt/str->inst "2006-06-06T06:06:06Z")
          d1 (tt/str->local-date-time "2012-12-12T12:12:12Z")
          d2 (tt/str->local-date-time "2006-06-06T06:06:06Z")]
      (is (= 12 (tt/get-minute i1)))
      (is (= 6 (tt/get-minute i2)))
      (is (= 12 (tt/get-minute d1)))
      (is (= 6 (tt/get-minute d2))))))

(t/deftest get-month
  (t/testing "easy-java-time.core/get-month"
    (let [i1 (tt/str->inst "2012-12-12T12:12:12Z")
          i2 (tt/str->inst "2006-06-06T06:06:06Z")
          d1 (tt/str->local-date-time "2012-12-12T12:12:12Z")
          d2 (tt/str->local-date-time "2006-06-06T06:06:06Z")]
      (is (= "DECEMBER" (str (tt/get-month i1))))
      (is (= "JUNE" (str (tt/get-month i2))))
      (is (= "DECEMBER" (str (tt/get-month d1))))
      (is (= "JUNE" (str (tt/get-month d2)))))))

(t/deftest get-month-value
  (t/testing "easy-java-time.core/get-month-value"
    (let [i1 (tt/str->inst "2012-12-12T12:12:12Z")
          i2 (tt/str->inst "2006-06-06T06:06:06Z")
          d1 (tt/str->local-date-time "2012-12-12T12:12:12Z")
          d2 (tt/str->local-date-time "2006-06-06T06:06:06Z")]
      (is (= 12 (tt/get-month-value i1)))
      (is (= 6 (tt/get-month-value i2)))
      (is (= 12 (tt/get-month-value d1)))
      (is (= 6 (tt/get-month-value d2))))))

(t/deftest get-second
  (t/testing "easy-java-time.core/get-second"
    (let [i1 (tt/str->inst "2012-12-12T12:12:12Z")
          i2 (tt/str->inst "2006-06-06T06:06:06Z")
          d1 (tt/str->local-date-time "2012-12-12T12:12:12Z")
          d2 (tt/str->local-date-time "2006-06-06T06:06:06Z")]
      (is (= 12 (tt/get-second i1)))
      (is (= 6 (tt/get-second i2)))
      (is (= 12 (tt/get-second d1)))
      (is (= 6 (tt/get-second d2))))))

(t/deftest get-year
  (t/testing "easy-java-time.core/get-year"
    (let [i1 (tt/str->inst "2012-12-12T12:12:12Z")
          i2 (tt/str->inst "2006-06-06T06:06:06Z")
          d1 (tt/str->local-date-time "2012-12-12T12:12:12Z")
          d2 (tt/str->local-date-time "2006-06-06T06:06:06Z")]
      (is (= 2012 (tt/get-year i1)))
      (is (= 2006 (tt/get-year i2)))
      (is (= 2012 (tt/get-year d1)))
      (is (= 2006 (tt/get-year d2))))))

(t/deftest inst->local-date-time
  (t/testing "easy-java-time.core/inst->local-date-time"
    (let [;; isntant
          i (tt/now)
          iy (tt/get-year i)
          im (tt/get-month-value i)
          id (tt/get-day-of-month i)
          ih (tt/get-hour i)
          it (tt/get-minute i)
          ic (tt/get-second i)
          ;; datetime
          d (tt/inst->local-date-time i)
          dy (tt/get-year d)
          dm (tt/get-month-value d)
          dd (tt/get-day-of-month d)
          dh (tt/get-hour d)
          dt (tt/get-minute d)
          dc (tt/get-second d)]
      (is (inst? i))
      (is (instance? Instant i))
      (is (instance? LocalDateTime d))
      (is (= iy dy))
      (is (= im dm))
      (is (= id dd))
      (is (= ih dh))
      (is (= it dt))
      (is (= ic dc)))))

(t/deftest local-date-time->inst
  (t/testing "easy-java-time.core/local-date-time->inst"
    (let [;; datetime
          d (tt/str->local-date-time "2012-12-12T12:12:12Z")
          dy (tt/get-year d)
          dm (tt/get-month-value d)
          dd (tt/get-day-of-month d)
          dh (tt/get-hour d)
          dt (tt/get-minute d)
          dc (tt/get-second d)
          ;; isntant
          i (tt/local-date-time->inst d)
          iy (tt/get-year i)
          im (tt/get-month-value i)
          id (tt/get-day-of-month i)
          ih (tt/get-hour i)
          it (tt/get-minute i)
          ic (tt/get-second i)]
      (is (inst? i))
      (is (instance? Instant i))
      (is (instance? LocalDateTime d))
      (is (= iy dy))
      (is (= im dm))
      (is (= id dd))
      (is (= ih dh))
      (is (= it dt))
      (is (= ic dc)))))

(t/deftest now
  (t/testing "easy-java-time.core/now"
    (is (inst? (tt/now)))
    (is (= (LocalDate/ofInstant (Instant/now) ZoneOffset/UTC)
           (LocalDate/ofInstant (tt/now) ZoneOffset/UTC)))))

(t/deftest str->inst
  (t/testing "easy-java-time.core/str->inst"
    (let [s "2012-12-12T12:12:12Z"
          i (tt/str->inst s)
          p (Instant/parse s)]
      (is (= p i)))))

(t/deftest str->local-date-time
  (t/testing "easy-java-time.core/str->local-date-time"
    (let [d (tt/str->local-date-time "2012-12-12T12:12:12Z")]
      (is (instance? LocalDateTime d))
      (is (= 2012 (tt/get-year d)))
      (is (= 12 (tt/get-minute d))))))

(t/deftest sub-inst
  (t/testing "easy-java-time.core/sub-inst"
    (let [i (tt/str->inst "2012-12-12T00:00:00Z")
          i-6years (tt/sub-inst i 6 :years)
          i-6years-6months (tt/sub-inst i-6years 6 :months)]
      (is (= 2012 (tt/get-year i)))
      (is (= 12 (tt/get-month-value i)))
      (is (= 12 (tt/get-day-of-month i)))
      (is (= 2006 (tt/get-year i-6years)))
      (is (= 6 (tt/get-month-value i-6years-6months)))
      (is (= 12 (tt/get-day-of-month i-6years)))
      (is (= 12 (tt/get-day-of-month i-6years-6months))))))

(t/deftest sub-local-date-time
  (t/testing "easy-java-time.core/sub-local-date-time"
    (let [d (tt/str->local-date-time "2012-12-12T00:00:00Z")
          d-6years (tt/sub-local-date-time d 6 :years)
          d-6years-6months (tt/sub-local-date-time d-6years 6 :months)]
      (is (= 2012 (tt/get-year d)))
      (is (= 12 (tt/get-month-value d)))
      (is (= 12 (tt/get-day-of-month d)))
      (is (= 2006 (tt/get-year d-6years)))
      (is (= 6 (tt/get-month-value d-6years-6months)))
      (is (= 12 (tt/get-day-of-month d-6years)))
      (is (= 12 (tt/get-day-of-month d-6years-6months))))))

(t/deftest inst->timestamp
  (t/testing "easy-java-time.core/inst->timestamp"
    (let [i (tt/str->inst "2012-12-12T00:00:00Z")
          t (tt/inst->timestamp i)]
      (is (= 1355270400000 t)))))

(t/deftest local-date-time->timestamp
  (t/testing "easy-java-time.core/local-date-time->timestamp"
    (let [d (tt/str->local-date-time "2012-12-12T00:00:00Z")
          t (tt/local-date-time->timestamp d)]
      (is (= 1355270400000 t)))))

(comment "TEST STUB"
#_:clj-kondo/ignore
(t/deftest xx
  (t/testing "easy-java-time.core/xx"
    (let []
      )))
)
