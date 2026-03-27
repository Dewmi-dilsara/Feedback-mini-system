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

  // Fetch feedback data
  useEffect(() => {

    if (!feedbackId) return;

    console.log(
      "Fetching feedback..."
    );

    fetch(
      `http://localhost:8080/api/public/feedback/${feedbackId}`
    )
      .then((res) => {

        if (!res.ok) {

          setMessage(
            "Feedback not found"
          );

          return null;

        }

        return res.json();

      })
      .then((json) => {

        if (json) {

          console.log(
            "API RESPONSE:",
            json
          );

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

    console.log(
      "Submitting rating:",
      rating
    );

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

        console.log(
          "Response:",
          text
        );

        // Handle response states

        if (
          text
            .toLowerCase()
            .includes("expired")
        ) {

          setMessage(
            data?.expiredReplyText ||
            "Feedback expired"
          );

        }
        else if (
          text
            .toLowerCase()
            .includes("already")
        ) {

          setMessage(
            "Already responded"
          );

        }
        else {

          setMessage(
            data?.thankYouText ||
            "Thank you for your feedback!"
          );

        }

      })
      .catch(() => {

        setMessage(
          "Failed to submit feedback"
        );

      });

  }

  // Show message state
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

  // Loading state
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

        ))}

      </div>

      <p>
        {data.footerText}
      </p>

    </div>

  );

}