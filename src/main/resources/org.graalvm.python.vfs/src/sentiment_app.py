import json

from vaderSentiment.vaderSentiment import SentimentIntensityAnalyzer

_ANALYZER = SentimentIntensityAnalyzer()


def analyze_review_json(review_text: str) -> str:
    scores = _ANALYZER.polarity_scores(review_text)
    label = "neutral"
    if scores["compound"] >= 0.05:
        label = "positive"
    elif scores["compound"] <= -0.05:
        label = "negative"

    return json.dumps(
        {
            "label": label,
            "compound": round(scores["compound"], 4),
            "positive": round(scores["pos"], 3),
            "neutral": round(scores["neu"], 3),
            "negative": round(scores["neg"], 3),
        }
    )
