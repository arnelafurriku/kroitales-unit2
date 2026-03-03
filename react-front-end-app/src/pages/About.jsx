function About() {
  return (
    <section className="container about">
      <h2 className="about-title">About</h2>

      <p className="about-lead">
        KroiTales is a kid-friendly React web app that helps parents and
        children co-create short, personalized bedtime stories.
      </p>

      <h3 className="about-sub">How it works</h3>
      <ul className="about-steps">
        <li>Pick a character, sidekick, setting, and action.</li>
        <li>Generate a story instantly and listen with Read Aloud.</li>
        <li>Save your favorites. They’ll stay in your browser.</li>
      </ul>
    </section>
  );
}
export default About;
