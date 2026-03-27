"use client";

import { useEffect, useState } from "react";

export default function AdminPage() {

  const enterpriseId = "enterprise-1";

  const [form, setForm] = useState<any>({
    headerText: "",
    headerDescription: "",
    footerText: "",
    ratingLabels: ["", "", "", "", ""],
    thankYouText: "",
    invalidReplyText: "",
    expiredReplyText: "",
    skipForChannels: []
  });

  const [error, setError] = useState("");

  useEffect(() => {

    fetch(
      `http://localhost:8080/api/admin/enterprises/${enterpriseId}/session-feedback-form`
    )
      .then((res) => res.json())
      .then((data) => {

        setForm(data);

      })
      .catch(() => {

        setError("Failed to load form");

      });

  }, []);

  const saveForm = () => {

    fetch(
      `http://localhost:8080/api/admin/enterprises/${enterpriseId}/session-feedback-form`,
      {
        method: "PUT",

        headers: {
          "Content-Type": "application/json"
        },

        body: JSON.stringify(form)
      }
    )
      .then(() => {

        alert("Saved successfully");

      })
      .catch(() => {

        setError("Save failed");

      });

  };

  return (

    <div style={{ padding: "20px" }}>

      <h1>Admin Feedback Form</h1>

      <input
        placeholder="Header Text"
        value={form.headerText}
        onChange={(e) =>
          setForm({
            ...form,
            headerText: e.target.value
          })
        }
      />

      <textarea
        placeholder="Header Description"
        value={form.headerDescription}
        onChange={(e) =>
          setForm({
            ...form,
            headerDescription: e.target.value
          })
        }
      />

      <h3>Rating Labels</h3>

      {form.ratingLabels.map(
        (label: string, index: number) => (

          <input
            key={index}
            placeholder={`Rating ${index + 1}`}
            value={label}
            onChange={(e) => {

              const updated =
                [...form.ratingLabels];

              updated[index] =
                e.target.value;

              setForm({
                ...form,
                ratingLabels: updated
              });

            }}
          />

      ))}

      <button
        onClick={saveForm}
        style={{ marginTop: "10px" }}
      >
        Save
      </button>

      {error && (

        <p style={{ color: "red" }}>
          {error}
        </p>

      )}

      <div
        style={{
          border: "1px solid gray",
          marginTop: "20px",
          padding: "10px"
        }}
      >

        <h2>Preview</h2>

        <h3>{form.headerText}</h3>

        <p>{form.headerDescription}</p>

        <div>

          {form.ratingLabels.map(
            (label: string, i: number) => (

              <button
  key={i}
  style={{
    margin: "5px",
    padding: "10px",
    border: "1px solid gray"
  }}
>
  {i + 1}
  <br />
  {label}
</button>

          ))}

        </div>

        <p>{form.footerText}</p>

      </div>

    </div>

  );
}