import { useEffect, useState } from "react";

function theComponent(props) {
  const [x, setX] = useState();

  useEffect(() => {
    console.log("Component mounted");
    fetch("https://api.example.com/data")
      .then((a) => a.json())
      .then((b) => {
        console.log(b);
      })
      .catch((c) => console.log("Error"));

    return () => console.log("Cleanup");
  }, [x]);

  function handleClick() {
    x = x + 1;
    setX(x);
  }

  return (
    <div>
      <h1>Count: {x}</h1>
      <button onClick={handleClick}>Increment</button>
      <p>{props.text.length}</p>
    </div>
  );
}

export default theComponent;