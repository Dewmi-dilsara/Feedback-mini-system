"use client";

import { useEffect, useState } from "react";

export default function FeedbackPage({
  params,
}: {
  params: Promise<{ feedbackId: string }>;
}) {
  const [feedbackId, setFeedbackId] = useState<string>("");
  const [data, setData] = useState<any>(null);

  // unwrap params (Next.js 16 fix)
  useEffect(() => {
    async function loadParams() {
      const p = await params;
      console.log("Feedback ID:", p.feedbackId);
      setFeedbackId(p.feedbackId);
    }

    loadParams();
  }, [params]);

  // fetch API
  useEffect(() => {
    if (!feedbackId) return;

    console.log("Fetching feedback...");

    fetch(
      `http://localhost:8080/api/public/feedback/${feedbackId}`
    )
      .then((res) => res.json())
      .then((json) => {
        console.log("API RESPONSE:", json);
        setData(json);
      })
      .catch((err) => {
        console.error("Fetch error:", err);
      });
  }, [feedbackId]);

  if (!data) {
    return <div>Loading feedback...</div>;
  }

  return (
    <div style={{ padding: "20px" }}>
      <h1>{data.headerText}</h1>

      <p>{data.headerDescription}</p>

      <ul>
        {data.ratingLabels?.map(
          (label: string, index: number) => (
            <li key={index}>{label}</li>
          )
        )}
      </ul>

      <p>{data.footerText}</p>
    </div>
  );
}