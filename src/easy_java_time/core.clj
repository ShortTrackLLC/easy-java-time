(ns easy-java-time.core
  "Deals with dates and time by wrapping common `java.time` libraries."
  {:added "0.1.0"
   :author "Chad Angelelli"}
  (:refer-clojure :exclude [format])
  (:import
   [java.time Instant LocalDateTime ZoneOffset]
   [java.time.temporal ChronoUnit TemporalAdjusters ]))

(defn now
  "Returns a `java.time.Instant` at present in UTC.

  Examples:

  ```clojure
  (require '[easy-java-time.core :as et])

  (et/now)
  ;= #object[java.time.Instant 0x776e1ae5 \"2024-11-29T03:32:48.798592Z\"]
  ```"
  []
  (Instant/now))

(defn inst->local-date-time
  "Returns a `java.time.LocalDateTime` for instant. `zone-offset` defaults to
  `java.time.ZoneOffset/UTC`.

  Examples:

  ```clojure
  (require '[easy-java-time.core :as et])

  (et/inst->local-date-time (et/now))
  ;= #object[java.time.LocalDateTime 0x933275d \"2024-11-29T03:45:02.302190\"]
  ```"
  [instant & [zone-offset]]
  (let [zone-offset (or zone-offset ZoneOffset/UTC)]
    (LocalDateTime/ofInstant instant zone-offset)))

(defn local-date-time->inst
  [local-date-time & [zone-offset]]
  (let [zone-offset (or zone-offset ZoneOffset/UTC)]
    (.toInstant local-date-time zone-offset)))

(defn chrono-unit
  "Returns `java.time.temporal.ChronoUnit/UNIT` for shorthand `unit`. All units
  except `:millennia` and `:forever` can be provided in singular or plural form
  - this is just for readability and has no effect on the value returned.

  | Unit                    |
  | ----------------------- |
  | `:day, :days`           |
  | `:decade, :decades`     |
  | `:hour, :hours`         |
  | `:micro, :micros`       |
  | `:milli, :millis`       |
  | `:minute, :minutes`     |
  | `:month, :months`       |
  | `:second, :seconds`     |
  | `:week, :weeks`         |
  | `:year, :years`         |

  Examples:

  ```clojure
  (require '[easy-java-time.core :as et])

  (:TODO :add-example)

  ```"
  [unit]
  (case unit
   (:days    :day)    ChronoUnit/DAYS
   (:decades :decade) ChronoUnit/DECADES
   (:hours   :hour)   ChronoUnit/HOURS
   (:micros  :micro)  ChronoUnit/MICROS
   (:millis  :milli)  ChronoUnit/MILLIS
   (:minutes :minute) ChronoUnit/MINUTES
   (:months  :month)  ChronoUnit/MONTHS
   (:seconds :second) ChronoUnit/SECONDS
   (:weeks   :week)   ChronoUnit/WEEKS
   (:years   :year)   ChronoUnit/YEARS))

(defn add-local-date-time
  "Returns `java.time.LocalDateTime` after adding `n` `unit`'s to it.

  Examples:

  ```clojure
  ```

  See also:

  - `easy-java-time.core/chrono-unit`"
  [ldt n unit]
  (.plus ldt n (chrono-unit unit)))

(defn sub-local-date-time
  "Returns `java.time.LocalDateTime` after subtracting `n` `unit`'s to it.

  Examples:

  ```clojure
  ```

  See also:

  - `easy-java-time.core/chrono-unit`"
  [ldt n unit]
  (.minus ldt n (chrono-unit unit)))

(defn add-inst
  "Returns `java.time.Instant` after adding `n` `unit`'s to it.

  Examples:

  ```clojure
  ```

  See also:

  - `easy-java-time.core/chrono-unit`"
  [instant n unit]
  (-> (inst->local-date-time instant)
      (add-local-date-time n unit)
      (.toInstant ZoneOffset/UTC)))

(defn sub-inst
  "Returns `java.time.Instant` after subtracting `n` `unit`'s to it.

  Examples:

  ```clojure
  ```

  See also:

  - `easy-java-time.core/chrono-unit`"
  [instant n unit]
  (-> (inst->local-date-time instant)
      (sub-local-date-time n unit)
      (.toInstant ZoneOffset/UTC)))

(defn- -get-temporal-field
  [field x]
  (let [ldt (if (inst? x)
              (inst->local-date-time x)
              x)]
    (case field
      :year         (.getYear ldt)
      :month        (.getMonth ldt)
      :month-value  (.getMonthValue ldt)
      :day-of-month (.getDayOfMonth ldt)
      :day-of-week  (.getDayOfWeek ldt)
      :day-of-year  (.getDayOfYear ldt)
      :hour         (.getHour ldt)
      :minute       (.getMinute ldt)
      :second       (.getSecond ldt))))

(defn get-year
  [instant-or-local]
  (-get-temporal-field :year instant-or-local))

(defn get-month
  [instant-or-local]
  (-get-temporal-field :month instant-or-local))

(defn get-month-value
  [instant-or-local]
  (-get-temporal-field :month-value instant-or-local))

(defn get-day-of-month
  [instant-or-local]
  (-get-temporal-field :day-of-month instant-or-local))

(defn get-day-of-week
  [instant-or-local]
  (-get-temporal-field :day-of-week instant-or-local))

(defn get-day-of-year
  [instant-or-local]
  (-get-temporal-field :day-of-year instant-or-local))

(defn get-hour
  [instant-or-local]
  (-get-temporal-field :hour instant-or-local))

(defn get-minute
  [instant-or-local]
  (-get-temporal-field :minute instant-or-local))

(defn get-second
  [instant-or-local]
  (-get-temporal-field :second instant-or-local))

(defn str->inst
  "Returns a `java.time.Instant` after parsing string `s`.

  Examples:

  ```clojure
  (require '[easy-java-time.core :as et])

  (et/str->inst \"2024-11-29T03:32:48.798592Z\")
  ;= #object[java.time.Instant 0x41694e7a \"2024-11-29T03:32:48.798592Z\"]
  ```"
  [s]
  (Instant/parse s))

(defn str->local-date-time
  [s]
  (-> s str->inst inst->local-date-time))

(defn inst->timestamp
  [instant]
  (.toEpochMilli instant))

(defn local-date-time->timestamp
  [local-date-time]
  (.toEpochMilli (local-date-time->inst local-date-time)))

;;TODO: added as stub, add tests
(defn first-day-of-month
  [instant-or-local]
  (let [x instant-or-local
        ldt (if (inst? x)
              (inst->local-date-time x)
              x)]
    (.with ldt (TemporalAdjusters/firstDayOfMonth))))

;;TODO: added as stub, add tests
(defn last-day-of-month
  [instant-or-local]
  (let [x instant-or-local
        ldt (if (inst? x)
              (inst->local-date-time x)
              x)]
    (.with ldt (TemporalAdjusters/lastDayOfMonth))))
