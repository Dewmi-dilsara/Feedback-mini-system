"use client";

import { useEffect, useState } from "react";

export default function FeedbackPage({
  params,
}: {
  params: Promise<{ feedbackId: string }>;
}) {

  const [feedbackId, setFeedbackId] =
    useState<string>("");

  const [data, setData] =
    useState<any>(null);

  const [message, setMessage] =
    useState<string>("");

  // Load feedbackId
  useEffect(() => {

    async function loadParams() {

      const p = await params;

      console.log(
        "Feedback ID:",
        p.feedbackId
      );

      setFeedbackId(
        p.feedbackId
      );

    }

    loadParams();

  }, [params]);

  // Fetch feedback
  useEffect(() => {

    if (!feedbackId) return;

    fetch(
      `http://localhost:8080/api/public/feedback/${feedbackId}`
    )

      .then(async (res) => {

        const json =
          await res.json();

        console.log(
          "API RESPONSE:",
          json
        );

        // Handle error response

        if (!res.ok) {

          setMessage(
            json.message ||
            "Feedback not found"
          );

          return null;

        }

        // Handle expired/responded

        if (json.message) {

          setMessage(
            json.message
          );

          return null;

        }

        return json;

      })

      .then((json) => {

        if (json) {

          setData(json);

        }

      })

      .catch(() => {

        setMessage(
          "Failed to load feedback"
        );

      });

  }, [feedbackId]);

  // Submit rating
  function submitRating(
    rating: number
  ) {

    fetch(
      `http://localhost:8080/api/public/feedback/${feedbackId}/respond`,
      {

        method: "POST",

        headers: {
          "Content-Type":
            "application/json"
        },

        body: JSON.stringify({
          rating
        }),
      }
    )

      .then(async (res) => {

        const text =
          await res.text();

        console.log(text);

        if (
          text.toLowerCase()
            .includes("expired")
        ) {

          setMessage(
            "This feedback link has expired."
          );

        }

        else if (
          text.toLowerCase()
            .includes("already")
        ) {

          setMessage(
            "Already responded"
          );

        }

        else {

          setMessage(
            "Thanks for your feedback!"
          );

        }

      })

      .catch(() => {

        setMessage(
          "Failed to submit"
        );

      });

  }

  // Show message screen
  if (message) {

    return (

      <div
        style={{
          padding: "20px"
        }}
      >

        <h2>
          {message}
        </h2>

      </div>

    );

  }

  // Loading screen
  if (!data) {

    return (
      <div>
        Loading feedback...
      </div>
    );

  }

  // Main UI
  return (

    <div
      style={{
        padding: "20px"
      }}
    >

      <h2>
        {data.headerText}
      </h2>

      <p>
        {data.headerDescription}
      </p>

      {/* Rating Buttons */}

      <div>

        {data.ratingLabels?.map(
          (
            label: string,
            index: number
          ) => (

            <button
              key={index}
              style={{
                margin: "5px",
                padding: "10px",
                border:
                  "1px solid gray"
              }}
              onClick={() =>
                submitRating(
                  index + 1
                )
              }
            >

              {index + 1}

              <br />

              {label}

            </button>

          )
        )}

      </div>

      <p>
        {data.footerText}
      </p>

    </div>

  );

}